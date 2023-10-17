package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
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
public class CreateEtablissementScolaireParameters {

    @NotBlank
    @NotNull
    private String nomEtablissement;

    @NotBlank
    @NotNull
    private Address address;

    @NotBlank
    @NotNull
    private Email email;

    @NotBlank
    @NotNull
    private LocalDateTime createdDate = LocalDateTime.now();
    @NotBlank
    @NotNull
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @NotBlank
    @NotNull
    private PhoneNumber phoneNumber;

    private Set<Eleve> eleves;

    private Set<Enseignant> enseignants;

    private Set<SalleDeClasse> salleDeClasses;

    private MultipartFile avatar;
}
