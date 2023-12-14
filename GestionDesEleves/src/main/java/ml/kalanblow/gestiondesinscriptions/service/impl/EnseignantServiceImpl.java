package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TokenType;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.request.*;
import ml.kalanblow.gestiondesinscriptions.response.AuthenticationResponse;
import ml.kalanblow.gestiondesinscriptions.security.KalanblowUserDetails;
import ml.kalanblow.gestiondesinscriptions.security.jwt.AuthenticationService;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtHelper;
import ml.kalanblow.gestiondesinscriptions.service.EnseignantService;
import ml.kalanblow.gestiondesinscriptions.service.RefreshTokenService;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class EnseignantServiceImpl implements EnseignantService , AuthenticationService {

    private final EnseignantRepository enseignantRepository;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EnseignantServiceImpl(EnseignantRepository enseignantRepository,JwtHelper jwtHelper,AuthenticationManager authenticationManager,
                                 RefreshTokenService refreshTokenService,BCryptPasswordEncoder passwordEncoder) {

        this.enseignantRepository = enseignantRepository;
        this.jwtHelper=jwtHelper;
        this.authenticationManager=authenticationManager;
        this.refreshTokenService=refreshTokenService;
        this.passwordEncoder=passwordEncoder;

    }

    /**
     * Recherche un enseignant par son identifiant unique.
     *
     * @param aLong L'identifiant unique de l'enseignant à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> findById(Long aLong) {
        log.info("Un  enseignant a été trouvé: {}", aLong);
        return enseignantRepository.findEnseignantById(aLong);
    }

    /**
     * Compte le nombre d'enseignants distincts associés à un cours d'enseignement spécifique.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel compter les enseignants.
     * @return Une instance facultative (Optional) du nombre d'enseignants distincts associés au cours d'enseignement, ou une instance vide si aucun enseignant n'est associé au cours.
     */
    @Override
    public Optional<Long> countDistinctByCoursDEnseignements(Cours coursDEnseignement) {

        log.info("Un cours a été trouvé pour cet enseignant: {}", coursDEnseignement);
        return enseignantRepository.countDistinctByCoursDEnseignements(coursDEnseignement);
    }

    /**
     * Recherche des enseignants par adresse e-mail en utilisant une correspondance partielle.
     *
     * @param email L'adresse e-mail à rechercher partiellement.
     * @return Une instance facultative (Optional) des enseignants trouvés, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> searchAllByEmailIsLike(Email email) {
        log.info("Un enseignat a été trouvé par cet email: {}", email);
        return enseignantRepository.findEnseignantByEmail(email);
    }

    /**
     * Obtient un enseignant en fonction du cours d'enseignement spécifié.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant n'est associé au cours d'enseignement.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignements(Cours coursDEnseignement) {
        log.info("Un Enseignant a été trouvé pour ce cours: {}", coursDEnseignement);
        return enseignantRepository.getEnseignantByCoursDEnseignements(coursDEnseignement);
    }

    /**
     * Obtient un enseignant en fonction de la plage de dates de création.
     *
     * @param debut La date de début de la plage de dates de création.
     * @param fin   La date de fin de la plage de dates de création.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCreatedDateIsBetween(LocalDate debut, LocalDate fin) {
        return enseignantRepository.getEnseignantByCreatedDateIsBetween(debut, fin);
    }

    /**
     * Obtient un enseignant en fonction du cours d'enseignement et des jours disponibles spécifiés.
     *
     * @param coursDEnseignement Le cours d'enseignement pour lequel rechercher l'enseignant.
     * @param enseignant         L'enseignant dont les jours disponibles doivent correspondre.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndJoursDisponibles(Cours coursDEnseignement, Enseignant enseignant) {

        log.info("Cours {} a été attribué au professeur {}", coursDEnseignement.getNomDuCours(), enseignant.getUserName());

        return enseignantRepository.getEnseignantByCoursDEnseignementsAndJoursDisponibles(coursDEnseignement, enseignant);
    }

    /**
     * Recherche un enseignant en fonction du cours d'enseignement, de l'enseignant, de l'heure de début de disponibilité,
     * de l'heure de fin de disponibilité et du jour disponible spécifiés.
     *
     * @param enseignant              L'enseignant pour lequel rechercher.
     * @param heureDebutDisponibilite L'heure de début de disponibilité à rechercher.
     * @param heureFinDisponibilite   L'heure de fin de disponibilité à rechercher.
     * @return Une instance facultative (Optional) de l'enseignant trouvé, ou une instance vide si aucun enseignant correspondant n'est trouvé.
     */
    @Override
    public Optional<Enseignant> getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(Enseignant enseignant, LocalTime heureDebutDisponibilite, LocalTime heureFinDisponibilite) {
        log.info("La disponibilité pour {} est du {} au {}", enseignant.getUserName(), heureDebutDisponibilite, heureFinDisponibilite);

        return enseignantRepository.getEnseignantByCoursDEnseignementsAndHeureDebutDisponibiliteAndAndHeureFinDisponibilite(enseignant, heureDebutDisponibilite, heureFinDisponibilite);
    }

    /**
     * Crée un nouvel enseignant en utilisant les paramètres spécifiés.
     *
     * @param enseignantParameters Les paramètres pour créer l'enseignant.
     * @return Une option contenant l'enseignant créé, ou une option vide en cas d'erreur.
     */
    @Override
    public Optional<Enseignant> creerEnseignant(CreateEnseignantParameters enseignantParameters) {

        log.info("Creating Enseignant {} ({})", enseignantParameters.getUserName().getFullName(), enseignantParameters.getEmail().asString());

        return Optional.of(ajouterUnNouveauEnseignant(enseignantParameters));
    }

    private static KalanblowUserDetails getUserDetails(Enseignant enseignant) {
        return new KalanblowUserDetails(enseignant);
    }

    /**
     * Modifie un enseignant existant en utilisant les paramètres spécifiés.
     *
     * @param id                       L'identifiant de l'enseignant à modifier.
     * @param editEnseignantParameters Les paramètres pour effectuer la modification.
     * @return Une option contenant l'enseignant modifié, ou une option vide en cas d'erreur.
     */
    @Override
    public Optional<Enseignant> editerEnseignant(Long id, EditEnseignantParameters editEnseignantParameters) {

        Enseignant enseignant = enseignantRepository.getReferenceById(id);
        if (editEnseignantParameters.getVersion() != enseignant.getVersion()) {

            throw  new ObjectOptimisticLockingFailureException(Enseignant.class, enseignant.getId());

        }
        Enseignant.EnseignantBuider enseignantBuider = new Enseignant.EnseignantBuider();
        enseignantBuider.heureFinDisponibilite(editEnseignantParameters.getHeureFinDisponibilite());
        enseignantBuider.coursDEnseignements(editEnseignantParameters.getCoursDEnseignements());
        enseignantBuider.joursDisponibilite(editEnseignantParameters.getJoursDisponibles());
        enseignantBuider.leMatricule(editEnseignantParameters.getLeMatricule());
        enseignantBuider.horaireClasses(editEnseignantParameters.getHoraireClasses());
        enseignantBuider.heureDebutDisponibilite(editEnseignantParameters.getHeureDebutDisponibilite());
        enseignantBuider.etablissementScolaire(editEnseignantParameters.getEtablissement());
        enseignantBuider.dateDeNaissance(editEnseignantParameters.getDateDeNaissance());
        enseignantBuider.build();
        enseignant = Enseignant.createEnseignatFromBuilder(enseignantBuider);
        log.info("Mise à jour des informations de l'Enseignant {} ({})", enseignant.getUserName().getFullName(), enseignant.getEmail().asString());
        editEnseignantParameters.updateEnseignant(enseignant);
        return Optional.of(enseignant);
    }

    @Override
    public Set<Enseignant> getAllEnseignants() {
        return  new HashSet<>(enseignantRepository.findAll());
    }

    @Override
    public void deleteById(Long id) {
        enseignantRepository.deleteEnseignantsById(id);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        return null;
    }

    // Function to map UserRole to a list of SimpleGrantedAuthority
    public static final Function<? super UserRole, ? extends List<SimpleGrantedAuthority>> mapUserRoleToAuthorities =
            userRole -> userRole.getPrivileges()
                    .stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                    .collect(Collectors.toList());
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        var eleve = enseignantRepository.findEnseignantByEmail(new Email(request.getEmail())).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var roles = eleve.getRoles().stream()
                .flatMap(role -> mapUserRoleToAuthorities.apply(role).stream())
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        var jwt = jwtHelper.generateToken((UserDetails) eleve);
        var refreshToken = refreshTokenService.createRefreshToken(eleve.getId());
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .roles(roles)
                .email(eleve.getEmail().asString())
                .id(eleve.getId())
                .refreshToken(refreshToken.getToken())
                .tokenType( TokenType.BEARER.name())
                .build();
    }

    /**
     * Cette méthode permet d'ajouter une photo au profil d'un élève s'il est fourni dans les paramètres.
     *
     * @param parameters Les paramètres de création de l'élève.
     * @param enseignant      L'élève auquel la photo doit être associée.
     * @throws KaladewnManagementException Si une erreur survient lors de la récupération ou de la sauvegarde de la photo.
     */
    private static void ajouterPhotoSiPresent(CreateEnseignantParameters parameters, Enseignant enseignant) {
        // Récupère le fichier de profil de l'Enseignant depuis les paramètres.
        MultipartFile profileEnseignant = parameters.getAvatar();

        if (profileEnseignant != null) {
            try {
                // Convertit le contenu du fichier en tableau de bytes et l'associe à l'élève.
                enseignant.setAvatar(profileEnseignant.getBytes());
            } catch (IOException e) {
                // En cas d'erreur lors de la récupération des bytes du fichier, lance une exception personnalisée.
                throw new KaladewnManagementException().throwException(EntityType.ELEVE, ExceptionType.ENTITY_NOT_FOUND, e.getMessage());
            }
        }
    }

    private Enseignant ajouterUnNouveauEnseignant(CreateEnseignantParameters createEnseignantParameters) {
        Enseignant enseignant = new Enseignant();
        enseignant.setCreatedDate(createEnseignantParameters.getCreatedDate());
        enseignant.setRoles(Collections.singleton(UserRole.TEACHER));
        enseignant.setUserName(createEnseignantParameters.getUserName());
        enseignant.setEmail(createEnseignantParameters.getEmail());
        enseignant.setCoursDEnseignements(createEnseignantParameters.getCoursDEnseignements());
        enseignant.setDisponible(true);
        enseignant.setPhoneNumber(createEnseignantParameters.getPhoneNumber());
        enseignant.setLeMatricule(createEnseignantParameters.getLeMatricule());
        enseignant.setEtablissement(createEnseignantParameters.getEtablissement());
        enseignant.setLastModifiedDate(createEnseignantParameters.getModifyDate());
        enseignant.setGender(createEnseignantParameters.getGender());
        enseignant.setDateDeNaissance(createEnseignantParameters.getDateDeNaissance());
        enseignant.setMaritalStatus(createEnseignantParameters.getMaritalStatus());
        enseignant.setJoursDisponibles(createEnseignantParameters.getJoursDisponibles());
        enseignant.setHeureFinDisponibilite(createEnseignantParameters.getHeureFinDisponibilite());
        enseignant.setAddress(createEnseignantParameters.getAddress());
        enseignant.setHeureDebutDisponibilite(createEnseignantParameters.getHeureDebutDisponibilite());
        enseignant.setDisponibilites(createEnseignantParameters.getHoraireClasses());
        ajouterPhotoSiPresent(createEnseignantParameters, enseignant);
        enseignant.setPassword(passwordEncoder.encode(createEnseignantParameters.getPassword()));
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Enseignant>> constraintViolations = validator.validate(enseignant, ValidationGroupOne.class);
        if (!constraintViolations.isEmpty()) {

            constraintViolations.forEach(c -> KaladewnManagementException.throwException(c.getMessage()));
        }

        Enseignant nouveauEnseignant1 = enseignantRepository.save(enseignant);
        log.info("Un enseignant a été enregistré {}", nouveauEnseignant1);
        var jwt = jwtHelper.generateToken(new KalanblowUserDetails(nouveauEnseignant1));
        var  refreshToken = refreshTokenService.createRefreshToken(nouveauEnseignant1.getId());
        var roles = nouveauEnseignant1.getRoles().stream().iterator().next().getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();

        AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(nouveauEnseignant1.getEmail().asString())
                .id(nouveauEnseignant1.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .tokenType( TokenType.BEARER.name())
                .build();
        return nouveauEnseignant1;
    }
}
