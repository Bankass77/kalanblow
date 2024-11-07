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
     * Recherche et retourne une liste d'élèves en fonction de leur prénom et nom de famille.
     *
     * @param prenom Le prénom de l'élève.
     * @param nomDeFamille Le nom de famille de l'élève.
     * @return Une liste d'élèves correspondant au nom et prénom spécifiés.
     */
    @Override
    public List<Eleve> findByUserUsername(final String prenom, final String nomDeFamille) {
        return eleveRepository.findByUserUserNamePrenomAndUserUserNameNomDeFamille(prenom, nomDeFamille);
    }

    /**
     * Recherche un élève en fonction de son email.
     *
     * @param email L'email de l'élève.
     * @return Un élève correspondant à l'email spécifié.
     * @throws KaladewnManagementException si aucun élève n'est trouvé avec cet email.
     */
    @Override
    public Optional<Eleve> findUserByEmail(final String email) {
        return Optional.ofNullable(eleveRepository.findByUserUserEmailEmail(email)
                .orElseThrow(() -> new KaladewnManagementException("Aucun élève trouvé avec l'email : " + email)));
    }

    /**
     * Recherche un élève en fonction de son numéro de téléphone.
     *
     * @param phoneNumber Le numéro de téléphone de l'élève.
     * @return Un élève correspondant au numéro de téléphone spécifié.
     * @throws KaladewnManagementException si aucun élève n'est trouvé avec ce numéro.
     */
    @Override
    public Optional<Eleve> findUserByPhoneNumber(final String phoneNumber) {
        return Optional.ofNullable(eleveRepository.findByUserUser_phoneNumberPhoneNumber(phoneNumber)
                .orElseThrow(() -> new KaladewnManagementException(
                        "Aucun élève trouvé avec ce numéro de téléphone : " + phoneNumber)));
    }

    /**
     * Recherche une liste d'élèves en fonction de leur nom et de leur classe.
     *
     * @param nom Le nom de famille de l'élève.
     * @param classe La classe de l'élève.
     * @return Une liste d'élèves correspondant au nom et à la classe spécifiés.
     */
    @Override
    public List<Eleve> findByNomAndClasse(final String nom, final String classe) {
        List<Eleve> eleves = eleveRepository.findByUserUserNameNomDeFamilleAndClasse(nom, classe);
        log.info("Liste des élèves selon leur classe: {}", eleves);
        return eleves;
    }

    /**
     * Recherche un élève en fonction de son identifiant national (INE).
     *
     * @param ineNumber L'identifiant national de l'élève.
     * @return Un élève correspondant à l'identifiant spécifié.
     */
    @Override
    public Optional<Eleve> findByIneNumber(final String ineNumber) {
        Optional<Eleve> eleve = eleveRepository.findByIneNumber(ineNumber);
        log.info("L'élève avec son ine: {}", eleve);
        return eleve;
    }

    /**
     * Inscrit un nouvel élève dans le système.
     * <p>
     * Cette méthode effectue plusieurs étapes : validation de l'unicité de l'email, génération d'un
     * identifiant national, calcul de l'âge, association des parents, établissement, classe et année scolaire,
     * puis sauvegarde de l'élève. Un message Kafka est également envoyé pour notifier l'inscription.
     *
     * @param eleve L'élève à inscrire.
     * @return L'élève nouvellement inscrit.
     * @throws KaladewnManagementException si l'email de l'élève n'est pas unique.
     */
    @Override
    public Eleve inscrireNouveauEleve(final Eleve eleve) {
        validateUniqueEmail(eleve);
        eleve.setIneNumber(KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength());
        eleve.setAge(CalculateUserAge.calculateAge(eleve.getDateDeNaissance()));

        setStudentRole(eleve.getUser());

        Set<Parent> parents = processParents(eleve);
        eleve.setParents(parents);

        Etablissement etablissement = processEtablissement(eleve);
        eleve.setEtablissement(etablissement);

        Classe classe = processClasse(eleve, etablissement);
        if (classe == null) {
            log.error("Classe non trouvée ou invalide pour l'élève : {}", eleve);
            throw new IllegalStateException("La classe de l'élève n'est pas valide");
        }
        eleve.setClasseActuelle(classe);

        assert classe != null;
        AnneeScolaire anneeScolaire = processAnneeScolaire(classe);
        List<AnneeScolaire> anneeScolaires = new ArrayList<>();
        anneeScolaires.add(anneeScolaire);
        eleve.setHistoriqueScolaires(anneeScolaires);
        Eleve savedEleve = eleveRepository.save(eleve);
        log.info("Inscription d'un nouvel élève: {}", savedEleve);

        try {
            kafkaProducer.sendMessage("Un nouvel élève a été créé", savedEleve.getEleveId().toString());
        } catch (Exception e) {
            log.error("Échec de l'envoi du message Kafka pour l'élève ID : {}", savedEleve.getEleveId(), e);
        }

        return savedEleve;
    }

    /**
     * Met à jour les informations d'un élève existant.
     *
     * @param id L'identifiant de l'élève à mettre à jour.
     * @param eleveDto Les nouvelles informations de l'élève.
     * @return L'élève mis à jour.
     * @throws EntityNotFoundException si l'élève avec l'identifiant spécifié n'existe pas.
     */
    @Override
    public Eleve mettreAjourEleve(final Long id, final Eleve eleveDto) {
        Eleve eleveToUpdate = eleveRepository.findEleveByEleveId(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Eleve.class));

        modelMapper.map(eleveDto, eleveToUpdate);

        Eleve updatedEleve = eleveRepository.save(eleveToUpdate);
        log.info("L'élève a été mis à jour: {}", updatedEleve);

        return updatedEleve;
    }

    /**
     * Supprime un élève en fonction de son identifiant.
     *
     * @param id L'identifiant de l'élève à supprimer.
     * @throws EntityNotFoundException si l'élève avec l'identifiant spécifié n'existe pas.
     */
    @Override
    public void supprimerEleve(final long id) {
        Optional<Eleve> eleve =eleveRepository.findEleveByEleveId(id);
        if (eleve.isPresent()) {
            eleveRepository.deleteEleveByEleveId(id);
        } else {
            throw new EntityNotFoundException(id, Eleve.class);
        }
    }

    /**
     * Recherche les élèves associés à un parent.
     *
     * @param parent Le parent dont on souhaite récupérer la liste des élèves.
     * @return Une liste d'élèves associés au parent spécifié.
     */
    @Override
    public Set<Eleve> getEleveParents(final Parent parent) {
        List<Eleve> eleves = eleveRepository.findByParents(parent);
        log.info("Liste des élèves par parent: {}", eleves);
        return  new HashSet<>(eleves);
    }

    /**
     * Recherche un élève en fonction de son identifiant.
     *
     * @param id L'identifiant de l'élève.
     * @return Un élève correspondant à l'identifiant spécifié.
     */
    @Override
    public Optional<Eleve> FindEleveById(final Long id) {
        Optional<Eleve> eleve = eleveRepository.findEleveByEleveId(id);
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

    /**
     * Assigne le rôle étudiant à l'utilisateur s'il ne l'a pas déjà.
     * <p>
     * Si l'utilisateur n'a pas de rôles assignés, un ensemble de rôles est créé
     * avec le rôle {@link UserRole#STUDENT}. Si des rôles existent déjà,
     * le rôle étudiant est simplement ajouté.
     *
     * @param user L'utilisateur auquel le rôle étudiant doit être assigné.
     */
    private void setStudentRole(User user) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Collections.singleton(UserRole.STUDENT)));
        } else user.getRoles().add(UserRole.STUDENT);
    }

    /**
     * Traite et retourne l'ensemble des parents d'un élève, en vérifiant leur existence dans la base de données.
     * <p>
     * Pour chaque parent associé à l'élève, la méthode vérifie si le parent existe dans le dépôt
     * en fonction de son identifiant. Si le parent n'existe pas, il est sauvegardé dans le dépôt.
     *
     * @param eleve L'élève dont on cherche le(s) parent(s).
     * @return Un ensemble de parents de l'élève, sauvegardés dans le dépôt si nécessaire.
     */
    private Set<Parent> processParents(Eleve eleve) {
        Set<Parent> parents = new HashSet<>();
        if (eleve.getParents() != null) {
            for (Parent parent : eleve.getParents()) {
                if (parent != null && parent.getUser() != null && parent.getUser().getUserName() != null) {
                    // Vérifie si parentId est non null avant de continuer
                    if (parent.getParentId() != null) {
                        Optional<Parent> parentExistant = parentRepository.findByParentId(parent.getParentId());
                        parents.add(parentExistant.orElseGet(() -> parentRepository.save(parent)));
                    } else {
                        // Logique pour gérer le cas où parentId est null
                        log.warn("Parent sans identifiant (parentId null) détecté, création possible...");
                        parents.add(parentRepository.save(parent));
                    }
                }
            }
        }
        return parents;
    }

    /**
     * Associe un élève à un établissement, en le créant si nécessaire.
     * <p>
     * Si un établissement avec l'identifiant scolaire ou le nom de l'établissement fourni existe déjà dans le dépôt,
     * il est retourné. Sinon, un nouvel établissement est sauvegardé dans le dépôt.
     *
     * @param eleve L'élève à inscrire au sein d'un établissement.
     * @return L'établissement associé à l'élève, créé ou récupéré du dépôt.
     */
    private Etablissement processEtablissement(Eleve eleve) {
        Etablissement etablissement = eleve.getEtablissement();
        if (etablissement != null && etablissement.getEtablisementScolaireId() != null) {
            return etablissementRepository.findByEtablisementScolaireId(etablissement.getEtablisementScolaireId())
                    .orElseGet(() -> etablissementRepository.save(etablissement));
        } else if (etablissement != null && etablissement.getNomEtablissement() != null) {
            return etablissementRepository.findEtablissementByNomEtablissement(etablissement.getNomEtablissement())
                    .orElseGet(() -> etablissementRepository.save(etablissement));
        }
        return null;
    }

    /**
     * Associe un élève à une classe dans un établissement donné, en créant la classe si nécessaire.
     * <p>
     * Si une classe associée à l'élève existe déjà dans le dépôt, elle est retournée.
     * Sinon, une nouvelle classe est créée et associée à l'établissement avant d'être sauvegardée dans le dépôt.
     *
     * @param eleve L'élève pour lequel on souhaite associer une classe.
     * @param etablissement L'établissement scolaire auquel la classe est associée.
     * @return La classe associée à l'élève et à l'établissement, créée ou récupérée du dépôt.
     */
    private Classe processClasse(Eleve eleve, Etablissement etablissement) {
        Classe classe = eleve.getClasseActuelle();
        if (classe != null) {
            List<Classe> result = classeRepository.findClasseByNom(classe.getNom());

            if (result.isEmpty()) {
                log.info("Classe introuvable, création d'une nouvelle classe avec le nom : {}", classe.getNom());
                classe.setEtablissement(etablissement);  // Associer l'établissement si nécessaire
                Classe savedClasse = classeRepository.save(classe); // Sauvegarde de la nouvelle classe
                log.info("Nouvelle classe créée et sauvegardée avec ID : {}", savedClasse.getClasseId());
                return savedClasse;
            } else {
                Classe foundClasse = result.get(0);
                if (foundClasse.getClasseId() == null) {
                    log.error("Classe trouvée sans identifiant (classeId) pour le nom : {}", classe.getNom());
                    throw new IllegalStateException("La classe de l'élève n'est pas valide - classeId manquant pour le nom : " + classe.getNom());
                }
                return foundClasse;
            }
        }
        log.error("Classe actuelle de l'élève non définie ou null");
        return null;
    }

    /**
     * Associe une année scolaire à une classe, en créant l'année scolaire si nécessaire.
     * <p>
     * Si l'année scolaire de la classe existe déjà dans le dépôt, elle est retournée.
     * Sinon, une nouvelle année scolaire est sauvegardée dans le dépôt.
     *
     * @param classe La classe pour laquelle on souhaite associer une année scolaire.
     * @return L'année scolaire associée à la classe, créée ou récupérée du dépôt.
     */
    private AnneeScolaire processAnneeScolaire(Classe classe) {
        AnneeScolaire anneeScolaire = classe.getAnneeScolaire();
        if (anneeScolaire != null) {
            // Vérifier si l'ID de l'année scolaire est non nul avant de chercher dans le repository
            if (anneeScolaire.getAnneeScolaireId() != null) {
                return anneeScolaireRepository.findAnneeScolaireByAnneeScolaireId(anneeScolaire.getAnneeScolaireId())
                        .orElseGet(() -> anneeScolaireRepository.save(anneeScolaire));
            } else {
                // Si anneeScolaireId est nul, loguez-le et sauvegardez l'année scolaire
                log.warn("Année scolaire sans identifiant (anneeScolaireId null) détectée, création d'une nouvelle année scolaire...");
                return anneeScolaireRepository.save(anneeScolaire);
            }
        }
        return null;
    }

    /**
     * Valide l'unicité de l'email de l'élève, en s'assurant qu'il n'est pas déjà utilisé par un autre élève.
     * <p>
     * Si un autre élève avec le même email existe, une exception est levée pour indiquer
     * l'existence d'un email en double.
     *
     * @param eleve L'élève dont on souhaite valider l'email.
     * @throws KaladewnManagementException si l'email est déjà utilisé par un autre élève.
     */
    private void validateUniqueEmail(Eleve eleve) {
        if (eleve.getUser() != null && eleve.getUser().getUserEmail() != null) {
            String email = eleve.getUser().getUserEmail().getEmail();
            Optional<Eleve> existingEleve = eleveRepository.findByUserUserEmailEmail(email);
            if (existingEleve.isPresent() && !existingEleve.get().getEleveId().equals(eleve.getEleveId())) {
                throw new KaladewnManagementException("Un élève avec cet email existe déjà : " + email);
            }
        }
    }

}
