package ml.kalanblow.gestiondesinscriptions.service.impl;


import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.CoursRepository;
import ml.kalanblow.gestiondesinscriptions.repository.EnseignantRepository;
import ml.kalanblow.gestiondesinscriptions.repository.MatiereRepository;
import ml.kalanblow.gestiondesinscriptions.request.CreateCoursParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditCoursParameters;
import ml.kalanblow.gestiondesinscriptions.service.CoursDEnseignementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class CoursDEnseignementServiceImp implements CoursDEnseignementService {

    private final CoursRepository coursDEnseignementRepository;
    private final MatiereRepository matiereRepository;

    private final EnseignantRepository enseignantRepository;

    @Autowired
    public CoursDEnseignementServiceImp(CoursRepository coursDEnseignementRepository, MatiereRepository matiereRepository, EnseignantRepository enseignantRepository) {

        this.coursDEnseignementRepository = coursDEnseignementRepository;
        this.matiereRepository = matiereRepository;
        this.enseignantRepository = enseignantRepository;
    }

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant et de l'année scolaire spécifiés.
     *
     * @param enseignant    L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param anneeScolaire L'année scolaire pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    @Override
    public Optional<Cours> findDistinctByEnseignantAndAnneeScolaire(Enseignant enseignant, Periode anneeScolaire) {
        return coursDEnseignementRepository.findDistinctByEnseignantAndAnneeScolaire(enseignant, anneeScolaire);
    }

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'horaire de classe, de la salle de classe et de la matière spécifiés.
     *
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param matiere       La matière pour laquelle rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    @Override
    public Optional<Cours> findDistinctByHoraireClassesAndAndSalleDeClasseAndMatiere(Horaire horaireClasse, Salle salleDeClasse, Matiere matiere) {
        return coursDEnseignementRepository.findDistinctByHorairesAndAndSalleAndMatiere(horaireClasse, salleDeClasse, matiere);
    }

    /**
     * Recherche un cours d'enseignement distinct en fonction de l'enseignant, de la salle de classe et de l'horaire de classe spécifiés.
     *
     * @param enseignant    L'enseignant pour lequel rechercher le cours d'enseignement.
     * @param salleDeClasse La salle de classe pour laquelle rechercher le cours d'enseignement.
     * @param horaireClasse L'horaire de classe pour lequel rechercher le cours d'enseignement.
     * @return Une instance facultative (Optional) du cours d'enseignement trouvé, ou une instance vide si aucun cours correspondant n'est trouvé.
     */
    @Override
    public Optional<Cours> findDistinctByEnseignantOrSalleDeClasseAndAndHoraireClasses(Enseignant enseignant, Salle salleDeClasse, Horaire horaireClasse) {

        Set<Horaire> horaireClasses = new HashSet<>();
        horaireClasses.add(horaireClasse);

        return coursDEnseignementRepository.findDistinctByEnseignantOrSalleAndAndHoraires(enseignant, salleDeClasse, obtenirUnHoraireDeClasse(horaireClasses));
    }

    @Override
    public Optional<Cours> creerCoursDEnseignement(EditCoursParameters nouveauCoursDEnseignement) {
        return Optional.empty();
    }

    /**
     * Crée un nouveau cours d'enseignement et le stocke dans le système.
     *
     * @param nouveauCoursDEnseignement Le cours d'enseignement à créer.
     * @return Un objet Optional contenant le cours d'enseignement créé s'il a été ajouté
     * avec succès, ou Optional.empty() si le cours n'a pas pu être ajouté.
     */
    @Override
    public Optional<Cours> creerCoursDEnseignement(CreateCoursParameters nouveauCoursDEnseignement) {

        Optional<Matiere> matiere = matiereRepository.getMatieresByNomMatiereContainsIgnoreCase(nouveauCoursDEnseignement.getMatiere().getNomMatiere());

        Cours coursDEnseignement = new Cours();
        coursDEnseignement.setEnseignant(nouveauCoursDEnseignement.getEnseignant());
        coursDEnseignement.setNomDuCours(nouveauCoursDEnseignement.getNomDuCours());
        coursDEnseignement.setHoraires(nouveauCoursDEnseignement.getHoraireClasses());
        coursDEnseignement.setNiveau(nouveauCoursDEnseignement.getNiveau());
        coursDEnseignement.setMatiere(nouveauCoursDEnseignement.getMatiere());
        coursDEnseignement.setAnneeScolaire(nouveauCoursDEnseignement.getAnneeScolaire());
        coursDEnseignement.setAbsenceEleves(nouveauCoursDEnseignement.getAbsenceEleves());
        coursDEnseignement.setHoraires(nouveauCoursDEnseignement.getSalleDeClasse().getHoraires());

        if (verifierConflitsHoraireDeClasse(coursDEnseignement) && matiere.isPresent()) {

            Cours coursDEnseignement1 = coursDEnseignementRepository.save(coursDEnseignement);
            return Optional.of(coursDEnseignement);

        }

        return Optional.empty();
    }

    /**
     * Édite un cours d'enseignement existant avec les paramètres spécifiés.
     *
     * @param editCoursDEnseignementParameters Les paramètres de l'édition du cours d'enseignement.
     * @return Un objet Optional contenant le cours d'enseignement édité si l'édition a été effectuée
     * avec succès, ou Optional.empty() si le cours n'a pas pu être édité.
     */
    @Override
    public Optional<Cours> editerCoursDEnseignement(Long id, EditCoursParameters editCoursDEnseignementParameters) {

        Optional<Cours> coursDEnseignement = coursDEnseignementRepository.findById(id);

        if (coursDEnseignement.isPresent()) {
            coursDEnseignement.get().setEnseignant(editCoursDEnseignementParameters.getEnseignant());
            coursDEnseignement.get().setNomDuCours(editCoursDEnseignementParameters.getNomDuCours());
            coursDEnseignement.get().setAbsenceEleves(editCoursDEnseignementParameters.getAbsenceEleves());
            coursDEnseignement.get().setAnneeScolaire(editCoursDEnseignementParameters.getAnneeScolaire());
            coursDEnseignement.get().setNiveau(editCoursDEnseignementParameters.getNiveau());
            coursDEnseignement.get().setMatiere(editCoursDEnseignementParameters.getMatiere());
            coursDEnseignement.get().setHoraires(editCoursDEnseignementParameters.getHoraireClasses());
            coursDEnseignement.get().setHoraires(editCoursDEnseignementParameters.getSalleDeClasse().getHoraires());

            return  coursDEnseignement;

        }
        return Optional.empty();
    }


    /**
     * Vérifie les conflits horaires d'un nouveau cours par rapport aux cours existants.
     *
     * @param coursDEnseignement Le nouveau cours à vérifier.
     * @return true si un conflit horaire est détecté, sinon false.
     */
    private boolean verifierConflitsHoraireDeClasse(Cours coursDEnseignement) {

        Set<Horaire> horairesDeClasse = coursDEnseignement.getHoraires();
        Salle salleDeClasse = coursDEnseignement.getSalle();

        boolean salleDeClasseOccupee = false;

        for (Horaire horaire : horairesDeClasse) {
            Optional<Cours> coursExiste = coursDEnseignementRepository.existsCoursDEnseignementBySalleAndHoraires(salleDeClasse, horaire);
            if (coursExiste.isPresent()) {
                salleDeClasseOccupee = true;
                break;
            }
        }

        boolean enseignantOccupe = false;
        for (Horaire horaireClasse : horairesDeClasse) {

            Optional<Cours> enseignant = coursDEnseignementRepository.existsCoursDEnseignementByEnseignantAndHoraires(coursDEnseignement.getEnseignant(), horaireClasse);

            if (enseignant.isPresent()) {
                enseignantOccupe = true;
                break;
            }
        }

        return salleDeClasseOccupee || enseignantOccupe;
    }


    /**
     * Obtient un horaire de classe à partir d'un ensemble d'horaires de classe.
     *
     * @param horairesDeClasse L'ensemble d'horaires de classe.
     * @return Un horaire de classe, ou null si l'ensemble est vide.
     */
    public Horaire obtenirUnHoraireDeClasse(Set<Horaire> horairesDeClasse) {
        if (horairesDeClasse != null && !horairesDeClasse.isEmpty()) {
            return horairesDeClasse.iterator().next();
        } else {
            return null;
        }
    }

}
