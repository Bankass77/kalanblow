package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.kafka.KafkaProducer;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.repository.AnneeScolaireRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ClasseRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EleveRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EtablissementRepository;
import ml.kalanblow.gestiondesinscriptions.repository.ParentRepository;
import ml.kalanblow.gestiondesinscriptions.service.EleveService;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;


@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final EleveRepository eleveRepository;
    private final ParentRepository parentRepository;
    private final ClasseRepository classeRepository;
    private final EtablissementRepository etablissementRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final ModelMapper modelMapper;

    private final KafkaProducer kafkaProducer;

    private final KaladewnManagementException kaladewnManagementException;


    @Autowired
    public EleveServiceImpl(final EleveRepository eleveRepository, final ParentRepository parentRepository,
                            final ClasseRepository classeRepository, final EtablissementRepository etablissementRepository
            , AnneeScolaireRepository anneeScolaireRepository, ModelMapper modelMapper,KafkaProducer kafkaProducer,KaladewnManagementException kaladewnManagementException) {
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRepository;
        this.classeRepository = classeRepository;
        this.etablissementRepository = etablissementRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.modelMapper = modelMapper;
        this.kafkaProducer = kafkaProducer;
        this.kaladewnManagementException= kaladewnManagementException;
    }


    /**
     * @param nomDeFamille le nom de l'élève
     * @param prenom       le prénom de l'élève
     * @return l'élève en fonction de UserName(nom+ nom de famille)
     */
    @Override
    public List<Eleve> findByUserUsername(final String prenom, final String nomDeFamille) {
        return eleveRepository.findByUserUserNamePrenomAndUserUserNameNomDeFamille(prenom, nomDeFamille);
    }

    /**
     * @param email de l'élève
     * @return un élève en fonction de son Email.
     */
    @Override
    public Optional<Eleve> findUserByEmail(final String email) {
        return Optional.ofNullable(eleveRepository.findByUserUserEmailEmail(email)
                .orElseThrow(() -> kaladewnManagementException
                        .throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, "Aucun élève trouvé avec l'email : " + email)));
    }

    /**
     * @param phoneNumber numéro de l'élève à chercher
     * @return eleve en fonction de son numéro de téléphone
     */
    @Override
    public Optional<Eleve> findUserByPhoneNumber(final String phoneNumber) {
        return Optional.ofNullable(eleveRepository.findByUserUser_phoneNumberPhoneNumber(phoneNumber).orElseThrow(() -> kaladewnManagementException.throwException(EntityType.PHONENUMBER, ExceptionType.ENTITY_EXCEPTION, "Aucun élève trouvé avec cet numéro de téléphone : " + phoneNumber)));
    }

    /**
     * @param nom    de l'élève
     * @param classe de l'élève
     * @return elève en fonction de sa classe
     */
    @Override
    public List<Eleve> findByNomAndClasse(final String nom, final String classe) {
        return eleveRepository.findByUserUserNameNomDeFamilleAndClasse(nom, classe);
    }

    /**
     * @param ineNumber identifiant de l'élève
     * @return un élève
     */
    @Override
    public Optional<Eleve> findByIneNumber(final String ineNumber) {

        try {
            return eleveRepository.findByIneNumber(ineNumber);
        } catch (Exception e) {
            throw kaladewnManagementException
                    .throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, "Le numéro INE de l'élève non trouvé.");
        }
    }

    /**
     * @param eleve qui doit être inscrit
     * @return nouveau Elève
     */
    @Override
    public Eleve inscrireNouveauEleve(final Eleve eleve) {

        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setAge(CalculateUserAge.calculateAge(eleve.getDateDeNaissance()));

        // Définir ou mettre à jour le rôle de l'élève
        if (eleve.getUser().getRoles() == null || eleve.getUser().getRoles().isEmpty()) {
            Set<UserRole> roles = new HashSet<>();
            roles.add(UserRole.STUDENT);
            eleve.getUser().setRoles(roles);
        } else {
            // Si l'élève a déjà des rôles, vous pouvez mettre à jour ou vérifier ici
            if (!eleve.getUser().getRoles().contains(UserRole.STUDENT)) {
                eleve.getUser().getRoles().add(UserRole.STUDENT); // Ajouter le rôle d'élève si ce n'est pas déjà présent
            }
        }

        Set<Parent> parents = new HashSet<>();

        for (Parent parent : eleve.getParents()) {
            if (parent != null && parent.getUser() != null && parent.getUser().getUserName() != null) {
                Optional<Parent> parentExistant = parentRepository.findByParentId(parent.getParentId());

                if (parentExistant.isPresent()) {
                    parents.add(parentExistant.get()); // Ajouter le parent existant
                } else {
                    parent = parentRepository.save(parent); // Sauvegarder le parent s'il n'existe pas
                    parents.add(parent); // Ajouter le parent nouvellement sauvegardé
                }
            }
        }
        eleve.setParents(parents);

        // Vérifier si l'établissement est null avant de travailler avec
        Etablissement etablissement = eleve.getEtablissement();
        if (etablissement != null && etablissement.getEtablisementScolaireId() != null) {
            // Vérifier si l'établissement existe dans la base de données
            Etablissement etablissementExistant = etablissementRepository.findByEtablisementScolaireId(etablissement.getEtablisementScolaireId());

            if (etablissementExistant != null) {
                // Si l'établissement existe, l'utiliser
                eleve.setEtablissement(etablissementExistant);
            } else {
                // Sinon, persister le nouvel établissement
                etablissement = etablissementRepository.save(etablissement);
                eleve.setEtablissement(etablissement);
            }
        } else {
            throw kaladewnManagementException
                    .throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, "Etablissement de l'élève non fourni.");
        }
        // Vérification de la classe de l'élève
        Classe classe = eleve.getClasseActuelle();
        if (classe != null) {
            Optional<Classe> classeActuelle = classeRepository.findById(classe.getClasseId());
            if (classeActuelle.isPresent()) {
                eleve.getClasseActuelle().getClasseId();
            } else {
                classe = classeRepository.save(classe);
                eleve.setClasseActuelle(classe);
            }
        }
        // Vérifier et persister l'année scolaire
        AnneeScolaire anneeScolaire = eleve.getClasseActuelle().getAnneeScolaire();
        if (anneeScolaire != null) {
            Optional<AnneeScolaire> anneeScolaireExistant = anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId());
            if (anneeScolaireExistant.isPresent()) {
                eleve.getClasseActuelle().setAnneeScolaire(anneeScolaireExistant.get());
            } else {
                anneeScolaire = anneeScolaireRepository.save(anneeScolaire);
                eleve.getClasseActuelle().setAnneeScolaire(anneeScolaire);
            }
        }
        kafkaProducer.sendMessage("Un nouvel élève a été crée", eleve.getEleveId().toString());
        // Enregistrer l'élève
        return eleveRepository.save(eleve);

    }




    /**
     * @param id       de l'élève à mettre à jour
     * @param eleveDto èlève à modifier
     * @return élève mise à jour
     */
    @Override
    public Eleve mettreAjourEleve(final Long id, final Eleve eleveDto) {
        Optional<Eleve> optionalEleve = eleveRepository.findById(id);

        if (optionalEleve.isPresent()) {
            Eleve eleveToUpdate = optionalEleve.get();

            // Mapper uniquement les champs non-nuls de eleveDto vers eleveToUpdate
            modelMapper.map(eleveDto, eleveToUpdate);

            eleveRepository.saveAndFlush(eleveToUpdate);
            return eleveToUpdate;
        } else {
            throw kaladewnManagementException
                    .throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, "Elève non trouvé.");
        }
    }

    /**
     * @param id de l'élève à supprimer
     */
    @Override
    public void supprimerEleve(final long id) {

        if (!eleveRepository.existsById(id)) {
            throw kaladewnManagementException.throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, "Aucun élève trouvé avec l'ID : " + id);
        }
        eleveRepository.deleteById(id);

    }

    /**
     * @param parent de l'élève
     * @return une liste d'élèves
     */
    @Override
    public List<Eleve> getEleveParents(final Parent parent) {

        try {
            return eleveRepository.findByParents(parent);
        } catch (Exception e) {
            throw kaladewnManagementException.throwException(EntityType.PARENT, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
    }

    /**
     * @param id de l'élève
     * @return un élève
     */
    @Override
    public Optional<Eleve> FindEleveById(final Long id) {

        try {
            return eleveRepository.findById(id);
        } catch (Exception e) {
            throw kaladewnManagementException.throwException(EntityType.ELEVE, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }
    }

    /**
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Eleve> getElevesPagine(final int page, final int size) {

        Pageable pageable = PageRequest.of(page, size);
        return eleveRepository.findAll(pageable);
    }

    /**
     * @param classe
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Eleve> getElevesPagineParClasse(final Classe classe, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eleveRepository.findByClasseActuelle(classe, pageable);
    }
}
