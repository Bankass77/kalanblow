package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingEtablissement;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotExistingEtablissement(groups = ValidationGroupTwo.class)
public class CreateEtablissementFormData {


    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private String nomEtablissement;


    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Address address;


    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private Email email;


    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private LocalDateTime createdDate = LocalDateTime.now();


    @NotNull
    @NotBlank
    @NotNull(groups = ValidationGroupOne.class)
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @NotBlank
    @NotNull
    @Pattern(regexp = "^(\\+223|00223)?[67]\\d{7}$")
    private PhoneNumber phoneNumber;

    private Set<Eleve> eleves;

    private Set<Enseignant> enseignants;

    private Set<Salle> salleDeClasses;

    private MultipartFile avatar;


    public CreateEtablissementParameters toEtablissementScolaireParameters() {

        CreateEtablissementParameters createEtablissementScolaireParameters = new CreateEtablissementParameters();

        createEtablissementScolaireParameters.setNomEtablissement(getNomEtablissement());
        createEtablissementScolaireParameters.setCreatedDate(getCreatedDate());
        createEtablissementScolaireParameters.setEmail(getEmail());
        createEtablissementScolaireParameters.setEleves(getEleves());
        createEtablissementScolaireParameters.setAddress(getAddress());
        createEtablissementScolaireParameters.setLastModifiedDate(getLastModifiedDate());
        createEtablissementScolaireParameters.setPhoneNumber(getPhoneNumber());
        createEtablissementScolaireParameters.setEnseignants(getEnseignants());
        createEtablissementScolaireParameters.setSalleDeClasses(getSalleDeClasses());
        MultipartFile avatar = getAvatar();
        if (avatar != null) {

            createEtablissementScolaireParameters.setAvatar(avatar);
        }
        return createEtablissementScolaireParameters;
    }


}
