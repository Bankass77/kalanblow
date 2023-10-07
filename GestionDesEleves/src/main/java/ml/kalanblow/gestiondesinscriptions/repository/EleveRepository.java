package ml.kalanblow.gestiondesinscriptions.repository;


import jakarta.validation.constraints.NotNull;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EleveRepository extends UserBaseRepository<Eleve>, JpaSpecificationExecutor<Eleve> {

    Optional<Eleve> findByPhoneNumber(PhoneNumber phoneNumber);
    Optional<Eleve> deleteEleveById(Long id);

    Optional<Eleve>  findElevesByEmail(@NotNull(message = "Please enter a valid address email.") Email email);


}
