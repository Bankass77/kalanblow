package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import lombok.*;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public sealed class CreateEnseignantParameters permits EditEnseignantParameters {

    private UserName userName;

    private Email email;

    private String leMatricule;

    private Gender gender;

    private LocalDate dateDeNaissance;

    private int age;

    private List<DayOfWeek> joursDisponibles;

    private LocalTime heureDebutDisponibilite;

    private LocalTime heureFinDisponibilite;

    private Etablissement etablissement;

    private List<Cours> coursDEnseignements;

    private MaritalStatus maritalStatus;

    private List<Horaire> horaireClasses;

    private Address address;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime modifyDate = LocalDateTime.now();

    private String password;

    private PhoneNumber phoneNumber;

    @Nullable
    private MultipartFile avatar;
}
