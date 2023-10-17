package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.util.CalculateUserAge;
import ml.kalanblow.gestiondesinscriptions.util.KaladewnUtility;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor

public sealed class CreateEleveParameters extends CreateUserParameters permits EditEleveParameters {

    @NotBlank
    @NotNull
    private LocalDate dateDeNaissance;
    @NotBlank
    @NotNull
    private int age=CalculateUserAge.calculateAge(dateDeNaissance);

    @NotBlank
    @NotNull
    private String studentIneNumber=KaladewnUtility.generatingandomAlphaNumericStringWithFixedLength();

    @NotBlank
    @NotNull
    private String motherFirstName;
    @NotBlank
    @NotNull
    private String motherLastName;
    @NotBlank
    @NotNull
    private PhoneNumber motherMobile;
    @NotBlank
    @NotNull
    private String fatherLastName;
    @NotBlank
    @NotNull
    private String fatherFirstName;
    @NotBlank
    @NotNull
    private PhoneNumber fatherMobile;
    @NotBlank
    @NotNull
    private Set<Role> roles;

    @NotBlank
    @NotNull
    private EtablissementScolaire etablissementScolaire;

    private List<AbsenceEleve> absences;

    /**
     * @param userName
     * @param gender
     * @param maritalStatus
     * @param email
     * @param password
     * @param phoneNumber
     * @param address
     * @param createdDate
     * @param modifyDate
     * @param dateDeNaissance
     * @param age
     * @param studentIneNumber
     * @param motherFirstName
     * @param motherLastName
     * @param motherMobile
     * @param fatherLastName
     * @param fatherFirstName
     * @param fatherMobile
     */
    public CreateEleveParameters(UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                                 String password, PhoneNumber phoneNumber, Address address, LocalDateTime createdDate,
                                 LocalDateTime modifyDate, LocalDate dateDeNaissance, int age, String studentIneNumber, String motherFirstName,
                                 String motherLastName, PhoneNumber motherMobile, String fatherLastName, String fatherFirstName,
                                 PhoneNumber fatherMobile, Set<Role> roles, EtablissementScolaire etablissementScolaire, List<AbsenceEleve> absences) {

        super(userName, gender, maritalStatus, email, password, phoneNumber, address, createdDate, modifyDate, roles);

        this.dateDeNaissance = dateDeNaissance;
        this.age = age;
        this.fatherFirstName = fatherFirstName;
        this.fatherLastName = fatherLastName;
        this.fatherMobile = fatherMobile;
        this.motherFirstName = motherFirstName;
        this.motherLastName = motherLastName;
        this.motherMobile = motherMobile;
        this.etablissementScolaire=etablissementScolaire;
        this.roles = roles;
        this.studentIneNumber=studentIneNumber;
        this.absences=absences;

    }


}
