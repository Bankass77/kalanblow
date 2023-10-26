package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.constraint.CustomValidation;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public  class CreateEleveFormData extends CreateUserFormData {



    @NotNull(groups = ValidationGroupOne.class)
    @NotBlank(message= "{ notnull.message}")
    @CustomValidation
    private String ineNumber;


    @NotNull(groups = ValidationGroupOne.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @CustomValidation
    private LocalDate dateDeNaissance;


    @NotNull(groups = ValidationGroupOne.class)
    @CustomValidation
    private int age;

    @NotNull(groups = ValidationGroupOne.class)
    @CustomValidation
    private Parent pere;

    @NotNull(groups = ValidationGroupOne.class)
    @CustomValidation
    private Parent mere;

    @NotNull(groups = ValidationGroupOne.class)
    @CustomValidation
    private Address address;

    @NotNull(groups = ValidationGroupOne.class)
    @CustomValidation
    private Etablissement etablissement;


    private String avatarBase64Encoded;

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
        createEleveParameters.setAddress(getAddress());
        createEleveParameters.setPassword(getPassword());
        createEleveParameters.setPere(getPere());
        createEleveParameters.setMere(getMere());
        createEleveParameters.setEmail(new Email(getEmail()));
        createEleveParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        createEleveParameters.setEtablissement(getEtablissement());


        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {

            createEleveParameters.setAvatar(getAvatarFile());
        }

        return createEleveParameters;
    }
    // end::toParameters[]
}
