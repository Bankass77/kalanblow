package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateEtablissementScolaireParameters {

    private String nomEtablissement;
    private Address address;
    private Email email;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
    private PhoneNumber phoneNumber;
    private Set<Eleve> eleves;
    private Set<Enseignant> enseignants;
    private Set<SalleDeClasse> salleDeClasses;
    private MultipartFile avatar;
}
