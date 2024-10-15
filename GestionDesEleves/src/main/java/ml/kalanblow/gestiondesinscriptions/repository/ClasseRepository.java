package ml.kalanblow.gestiondesinscriptions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondesinscriptions.model.AnneeScolaire;
import ml.kalanblow.gestiondesinscriptions.model.Classe;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
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

    Optional<Classe> findByClasseId(long id);

}
