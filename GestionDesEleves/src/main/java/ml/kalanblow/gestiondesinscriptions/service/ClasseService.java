package ml.kalanblow.gestiondesinscriptions.service;

import java.util.List;
import java.util.Optional;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;

public interface ClasseService {

    // Créer une nouvelle classe
    Classe createClasse(Classe classe);

    // Mettre à jour une classe existante
    Classe updateClasse(Long classeId, Classe classe);

    // Supprimer une classe par ID
    void deleteClasse(Long classeId);

    Optional<Classe> findByClasseId(long id);

    // Trouver par le nom de la classe
    List<Classe> findByNom(String nom);

    // Trouver par l'établissement
    List<Classe> findByEtablissement(Etablissement etablissement);

    // Trouver par l'année scolaire
    List<Classe> findByAnneeScolaire(AnneeScolaire anneeScolaire);

    // Trouver une classe par ID et par l'établissement
    Optional<Classe> findByClasseIdAndEtablissement(Long classeId, Etablissement etablissement);

    // Compter le nombre de classes par établissement
    Long countByEtablissement(Etablissement etablissement);

    Optional<Classe> findByClasseName( String nom);
}