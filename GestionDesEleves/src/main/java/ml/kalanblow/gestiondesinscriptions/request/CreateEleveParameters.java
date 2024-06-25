package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEleveParameters permits EditEleveParameters {

    private UserName userName;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Email email;

    private String password;

    private PhoneNumber phoneNumber;

    private Address address;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime modifyDate = LocalDateTime.now();

    @Nullable
    private MultipartFile avatar;

    private LocalDate dateDeNaissance;

    private int age;

    private String studentIneNumber;

    private Parent pere;

    private Parent mere;

    private Etablissement etablissement;

    private List<Absence> absences;

}
