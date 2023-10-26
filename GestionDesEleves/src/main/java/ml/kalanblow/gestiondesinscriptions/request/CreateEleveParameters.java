package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private int age;

    @NotBlank
    @NotNull
    private String studentIneNumber;

    @NotBlank
    @NotNull
    private Parent pere;

    @NotBlank
    @NotNull
    private Parent mere;

    @NotBlank
    @NotNull
    private Etablissement etablissement;

    private List<Absence> absences;

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
     * @param absences
     * @param etablissement
     * @param pere
     * * @param mere
     */
    public CreateEleveParameters(UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                                 String password, PhoneNumber phoneNumber, Address address, LocalDateTime createdDate,
                                 LocalDateTime modifyDate, LocalDate dateDeNaissance, int age, String studentIneNumber,Parent pere , Parent mere,Etablissement etablissement, List<Absence> absences) {

        super(userName, gender, maritalStatus, email, password, phoneNumber, address, createdDate, modifyDate);

        this.dateDeNaissance = dateDeNaissance;
        this.age = age;
        this.pere=pere;
        this.mere=mere;
        this.etablissement = etablissement;
        this.studentIneNumber=studentIneNumber;
        this.absences=absences;

    }


}
