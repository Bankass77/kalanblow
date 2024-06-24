package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppelDePresenceService {

    Optional<Presence> findDistinctByCoursAndEleveUserNameOrderByEleve(Cours coursDEnseignement, Eleve eleve);

    Optional<Presence> findAppelDePresenceByCours_Enseignant(Cours coursDEnseignement);

    Optional<Presence> findAppelDePresenceByCours_HoraireClasses(Horaire horaireClasse);

    void effectuerAppelDesEleves(LocalDate dateActuelle, Salle salleDeClasse, LocalTime heureDebut, LocalTime heureFin);

    List<Presence> findByDateAppel(LocalDate dateAppel);

    void enregistrerPresenceEleve(Eleve eleve, LocalDate dateActuelle, Cours cours);
}
