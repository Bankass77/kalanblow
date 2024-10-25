package ml.kalanblow.gestiondesinscriptions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.kalanblow.gestiondesinscriptions.model.Administrateur;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
    Optional<Administrateur> findByUserUserEmailEmail(String email);

}
