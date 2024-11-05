package ml.kalanblow.gestiondesinscriptions.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.kafka.KafkaProducer;
import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import ml.kalanblow.gestiondesinscriptions.model.User;
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
@Slf4j
public class EleveServiceImpl implements EleveService {

    private final EleveRepository eleveRepository;
    private final ParentRepository parentRepository;
    private final ClasseRepository classeRepository;
    private final EtablissementRepository etablissementRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;


    @Autowired
    public EleveServiceImpl(final EleveRepository eleveRepository, final ParentRepository parentRepository,
                            final ClasseRepository classeRepository, final EtablissementRepository etablissementRepository
            , AnneeScolaireRepository anneeScolaireRepository, ModelMapper modelMapper, KafkaProducer kafkaProducer) {
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRepository;
        this.classeRepository = classeRepository;
        this.etablissementRepository = etablissementRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.modelMapper = modelMapper;
        this.kafkaProducer = kafkaProducer;

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
                .orElseThrow(() -> new KaladewnManagementException("Aucun élève trouvé avec l'email : " + email)));
    }

    /**
     * @param phoneNumber numéro de l'élève à chercher
     * @return eleve en fonction de son numéro de téléphone
     */
    @Override
    public Optional<Eleve> findUserByPhoneNumber(final String phoneNumber) {
        return Optional.ofNullable(eleveRepository.findByUserUser_phoneNumberPhoneNumber(phoneNumber)
                .orElseThrow(() -> new KaladewnManagementException(
                        "Aucun élève trouvé avec cet numéro de téléphone : " + phoneNumber)));
    }

    /**
     * @param nom    de l'élève
     * @param classe de l'élève
     * @return elève en fonction de sa classe
     */
    @Override
    public List<Eleve> findByNomAndClasse(final String nom, final String classe) {
        List<Eleve> eleves = eleveRepository.findByUserUserNameNomDeFamilleAndClasse(nom, classe);
        log.info("L'élève avec son ine: {}", eleves);
        return eleves;
    }

    /**
     * @param ineNumber identifiant de l'élève
     * @return un élève
     */
    @Override
    public Optional<Eleve> findByIneNumber(final String ineNumber) {
        Optional<Eleve> eleve = eleveRepository.findByIneNumber(ineNumber);
        log.info("L'élève avec son ine: {}", eleve);
        return eleve;

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
            setStudentRole(eleve.getUser());
        } else {
            setStudentRole(eleve.getUser());
        }

        Set<Parent> parents = processParents(eleve);
        eleve.setParents(parents);

        // Vérifier si l'établissement est null avant de travailler avec
        Etablissement etablissement =processEtablissement(eleve);
        eleve.setEtablissement(etablissement);

        // Vérification de la classe de l'élève
        Classe classe = processClasse(eleve, etablissement);
        eleve.setClasseActuelle(classe);
        // Vérifier et persister l'année scolaire
        AnneeScolaire anneeScolaire = processAnneeScolaire(classe);
        eleve.setHistoriqueScolaires(List.of(anneeScolaire));

        // Persister l'élève dans la base de données
        Eleve savedEleve = eleveRepository.save(eleve);
        log.info("Inscription d'un nouvel élève: {}", savedEleve);

        // Étape 2 : Essayer d'envoyer un message Kafka
        try {
            kafkaProducer.sendMessage("Un nouvel élève a été créé", savedEleve.getEleveId().toString());
        } catch (Exception e) {
            log.error("Échec de l'envoi du message Kafka pour l'élève ID : {}", savedEleve.getEleveId(), e);
            // Vous pouvez aussi enregistrer cette erreur pour une tentative ultérieure si nécessaire
        }

        return savedEleve;
    }

    /**
     * @param id       de l'élève à mettre à jour
     * @param eleveDto èlève à modifier
     * @return élève mise à jour
     */

    @Override
    public Eleve mettreAjourEleve(final Long id, final Eleve eleveDto) {
        Eleve eleveToUpdate = eleveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Eleve.class));

        // Mapper uniquement les champs non-nuls de eleveDto vers eleveToUpdate
        modelMapper.map(eleveDto, eleveToUpdate);

        Eleve updatedEleve = eleveRepository.save(eleveToUpdate);
        log.info("L'élève a été mis à jour: {}", updatedEleve);

        return updatedEleve;
    }

    /**
     * @param id de l'élève à supprimer
     */
    @Override
    public void supprimerEleve(final long id) {

        if (eleveRepository.existsById(id)) {
            eleveRepository.deleteById(id);
        } else {

            throw new EntityNotFoundException(id, Eleve.class);
        }
    }

    /**
     * @param parent de l'élève
     * @return une liste d'élèves
     */
    @Override
    public List<Eleve> getEleveParents(final Parent parent) {

        List<Eleve> eleves = eleveRepository.findByParents(parent);
        log.info("Liste des élève par parent: {}", eleves);
        return eleves;
    }

    /**
     * @param id de l'élève
     * @return un élève
     */
    @Override
    public Optional<Eleve> FindEleveById(final Long id) {
        Optional<Eleve> eleve = eleveRepository.findById(id);
        log.info("L'élève avec cet id: {}", eleve);
        return eleve;
    }

    /**
     * @param page en cours
     * @param size taille de la liste des élèves
     * @return une pagination de la liste des élèves.
     */
    @Override
    public Page<Eleve> getElevesPagine(final int page, final int size) {

        Pageable pageable = PageRequest.of(page, size);
        return eleveRepository.findAll(pageable);
    }

    /**
     * @param classe de l'élève
     * @param page   en cours
     * @param size   taille de la liste
     * @return une liste des élèves par classe.
     */
    @Override
    public Page<Eleve> getElevesPagineParClasse(final Classe classe, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eleveRepository.findByClasseActuelle(classe, pageable);
    }

    private void setStudentRole(User user) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Collections.singleton(UserRole.STUDENT)));
        } else if (!user.getRoles().contains(UserRole.STUDENT)) {
            user.getRoles().add(UserRole.STUDENT);
        }
    }

    private Set<Parent> processParents(Eleve eleve) {
        Set<Parent> parents = new HashSet<>();
        if (eleve.getParents() != null) {
            for (Parent parent : eleve.getParents()) {
                if (parent != null && parent.getUser() != null && parent.getUser().getUserName() != null) {
                    Optional<Parent> parentExistant = parentRepository.findByParentId(parent.getParentId());
                    parents.add(parentExistant.orElseGet(() -> parentRepository.save(parent)));
                }
            }
        }
        return parents;
    }

    private Etablissement processEtablissement(Eleve eleve) {
        Etablissement etablissement = eleve.getEtablissement();
        if (etablissement != null && etablissement.getEtablisementScolaireId() != null) {
            return etablissementRepository.findByEtablisementScolaireId(etablissement.getEtablisementScolaireId())
                    .orElseGet(() -> etablissementRepository.save(etablissement));
        }
        return null;
    }

    private Classe processClasse(Eleve eleve, Etablissement etablissement) {
        Classe classe = eleve.getClasseActuelle();
        if (classe != null) {
            return classeRepository.findById(classe.getClasseId())
                    .orElseGet(() -> {
                        classe.setEtablissement(etablissement);
                        return classeRepository.save(classe);
                    });
        }
        return null;
    }

    private AnneeScolaire processAnneeScolaire(Classe classe) {
        AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
        if (anneeScolaire != null) {
            return anneeScolaireRepository.findById(anneeScolaire.getAnneeScolaireId())
                    .orElseGet(() -> anneeScolaireRepository.save(anneeScolaire));
        }
        return null;
    }

}
