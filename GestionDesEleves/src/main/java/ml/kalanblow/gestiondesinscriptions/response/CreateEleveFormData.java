package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateEleveParameters;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;

import java.time.LocalDate;
import java.time.LocalDateTime;

@PasswordsMatch(groups = ValidationGroupTwo.class)
@NotExistingUser(groups = ValidationGroupTwo.class)
public class CreateEleveFormData extends CreateUserFormData {
    @NotBlank
    @NotNull
    private String ineNumber = KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength();
    @NotBlank
    @NotNull
    private LocalDate dateDeNaissance;

    @NotBlank
    @NotNull
    private int age;

    @NotBlank
    @NotNull
    private String motherFirstName;
    @NotBlank
    @NotNull
    private String motherLastName;
    @NotBlank
    @NotNull
    private String fatherFirstName;

    @NotBlank
    @NotNull
    private String fatherLastName;

    @NotBlank
    @NotNull
    private EtablissementScolaire etablissementScolaire;

    public CreateEleveParameters toEleveParameters() {

        CreateEleveParameters createEleveParameters = new CreateEleveParameters();
        createEleveParameters.setDateDeNaissance(dateDeNaissance);
        createEleveParameters.setStudentIneNumber(ineNumber);
        createEleveParameters.setAge(CalculateUserAge.calculateAge(dateDeNaissance));
        createEleveParameters.setModifyDate(LocalDateTime.now());
        createEleveParameters.setMotherFirstName(motherFirstName);
        createEleveParameters.setMotherLastName(motherLastName);
        createEleveParameters.setMaritalStatus(getMaritalStatus());
        createEleveParameters.setMotherMobile(getPhoneNumber());
        createEleveParameters.setRoles(getRoles());
        createEleveParameters.setFatherMobile(getPhoneNumber());
        createEleveParameters.setCreatedDate(getCreatedDate());
        createEleveParameters.setPhoneNumber(getPhoneNumber());
        createEleveParameters.setGender(getGender());
        createEleveParameters.setPassword(getPassword());
        createEleveParameters.setAddress(getAddress());
        createEleveParameters.setFatherLastName(fatherLastName);
        createEleveParameters.setFatherFirstName(fatherFirstName);
        createEleveParameters.setEmail(new Email(getEmail()));
        createEleveParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        createEleveParameters.setEtablissementScolaire(etablissementScolaire);

        return createEleveParameters;
    }
}
