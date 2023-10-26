package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateEleveFormData extends CreateUserFormData {


    @NotNull(groups = ValidationGroupOne.class)
    private String ineNumber;


    @NotNull(groups = ValidationGroupOne.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDeNaissance;

    @NotNull
    private int age;

    @NotNull(groups = ValidationGroupOne.class)
    private Parent pere;

    @NotNull(groups = ValidationGroupOne.class)
    private Parent mere;


    @NotNull(groups = ValidationGroupOne.class)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String password;


    @NotNull(groups = ValidationGroupOne.class)

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String passwordRepeated;


    @NotNull(groups = ValidationGroupOne.class)
    private Address address;


    @NotNull(groups = ValidationGroupOne.class)
    private Etablissement etablissement;


    // tag::toParameters[]
    public CreateEleveParameters toEleveParameters() {

        CreateEleveParameters createEleveParameters = new CreateEleveParameters();
        createEleveParameters.setDateDeNaissance(getDateDeNaissance());
        createEleveParameters.setStudentIneNumber(getIneNumber());
        createEleveParameters.setAge(CalculateUserAge.calculateAge(getDateDeNaissance()));
        createEleveParameters.setModifyDate(LocalDateTime.now());
        createEleveParameters.setMaritalStatus(getMaritalStatus());

        createEleveParameters.setCreatedDate(getCreatedDate());
        createEleveParameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        createEleveParameters.setGender(getGender());
        createEleveParameters.setPassword(getPassword());
        createEleveParameters.setAddress(getAddress());
        createEleveParameters.setPere(getPere());
        createEleveParameters.setMere(getMere());
        createEleveParameters.setEmail(new Email(getEmail()));
        createEleveParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        createEleveParameters.setEtablissement(getEtablissement());
        createEleveParameters.setPassword(getPassword());

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {

            createEleveParameters.setAvatar(getAvatarFile());
        }

        return createEleveParameters;
    }
    // end::toParameters[]
}
