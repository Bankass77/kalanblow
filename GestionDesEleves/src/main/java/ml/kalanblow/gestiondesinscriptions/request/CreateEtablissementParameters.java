package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateEtablissementParameters {

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private String nomEtablissement;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private Address address;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private Email email;

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private LocalDateTime createdDate = LocalDateTime.now();
    @NotBlank
     @NotNull(message = "{notnull.message}")
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @NotBlank
     @NotNull(message = "{notnull.message}")
    private PhoneNumber phoneNumber;

    private Set<Eleve> eleves;

    private Set<Enseignant> enseignants;

    private Set<Salle> salleDeClasses;

    private MultipartFile avatar;
}
