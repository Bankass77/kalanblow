package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;

import java.time.LocalDate;
import java.time.LocalDateTime;

@PasswordsMatch(groups = ValidationGroupTwo.class)
@NotExistingUser(groups = ValidationGroupTwo.class)
@Data
@EqualsAndHashCode(callSuper=false)
public class CreateEleveFormData extends CreateUserFormData {
    
    @NotNull
    private String ineNumber = KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength();

    @NotNull
    private LocalDate dateDeNaissance;
    
    @NotNull
    private int age;

    @NotNull
    private String motherFirstName;

    @NotNull
    private String motherLastName;

    private String motherPhoneNumber;

    @NotNull
    private String fatherLastName;

    @NotNull
    private String fatherFirstName;

    private String fatherPhoneNumber;

    @NotBlank
    private String password;
    @NotBlank
    private String passwordRepeated;

    @NotNull
    private Address address;

    @NotNull
    private Etablissement etablissement;


    // tag::toParameters[]
    public CreateEleveParameters toEleveParameters() {

        CreateEleveParameters createEleveParameters = new CreateEleveParameters();
        createEleveParameters.setDateDeNaissance(getDateDeNaissance());
        createEleveParameters.setStudentIneNumber(getIneNumber());
        createEleveParameters.setAge(CalculateUserAge.calculateAge(getDateDeNaissance()));
        createEleveParameters.setModifyDate(LocalDateTime.now());
        createEleveParameters.setMotherFirstName(getMotherFirstName());
        createEleveParameters.setMotherLastName(getMotherLastName());
        createEleveParameters.setMaritalStatus(getMaritalStatus());
        createEleveParameters.setMotherMobile(new PhoneNumber(getMotherPhoneNumber()));
        createEleveParameters.setFatherMobile(new PhoneNumber(getPhoneNumber()));
        createEleveParameters.setCreatedDate(getCreatedDate());
        createEleveParameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        createEleveParameters.setGender(getGender());
        createEleveParameters.setPassword(getPassword());
        createEleveParameters.setAddress(getAddress());
        createEleveParameters.setFatherLastName(getFatherLastName());
        createEleveParameters.setFatherFirstName(getFatherFirstName());
        createEleveParameters.setFatherMobile(new PhoneNumber(getFatherPhoneNumber()));
        createEleveParameters.setEmail(new Email(getEmail()));
        createEleveParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        createEleveParameters.setEtablissement(getEtablissement());
        createEleveParameters.setPassword(getPassword());
        if(getAvatarFile() !=null  && !getAvatarFile().isEmpty()){

            createEleveParameters.setAvatar(getAvatarFile());
        }

        return createEleveParameters;
    }
    // end::toParameters[]
}
