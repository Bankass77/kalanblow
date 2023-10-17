package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementScolaireParameters;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEtablissementScolaireFormData {

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


    public CreateEtablissementScolaireParameters toEtablissementScolaireParameters() {

        CreateEtablissementScolaireParameters createEtablissementScolaireParameters = new CreateEtablissementScolaireParameters();

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
