package ml.kalanblow.gestiondesinscriptions.repository;

import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



/**
 * Interface pour la gestion des opérations de persistance des établissements scolaires.
 * Cette interface étend JpaRepository pour fournir des fonctionnalités de base de persistance,
 * ainsi que JpaSpecificationExecutor pour permettre les requêtes avancées basées sur des spécifications.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor
 */
@Repository
public interface EtablissementScolaireRepository extends JpaRepository<EtablissementScolaire, JpaSpecificationExecutor<EtablissementScolaire>> {

    EtablissementScolaire findByNomEtablissement (String nomEtablissement);

    EtablissementScolaire findByEtablisementScolaireId (long id);

}
