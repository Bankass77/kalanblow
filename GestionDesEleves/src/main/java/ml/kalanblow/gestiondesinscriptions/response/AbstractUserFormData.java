package ml.kalanblow.gestiondesinscriptions.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.constraint.CustomValidation;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupOne;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@NotExistingUser(groups = ValidationGroupTwo.class)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public sealed class AbstractUserFormData permits  CreateUserFormData, EditEleveFormData {


    @NotBlank
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    @CustomValidation
    private String prenom;

    @NotBlank
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    @CustomValidation
    private String nomDeFamille;

    @NotBlank(message= "{notnull.message}")
    @CustomValidation
    private String phoneNumber;


    @CustomValidation
    private UserRole userRole;


    @CustomValidation
    private Gender gender;


     @CustomValidation
    private MaritalStatus maritalStatus;

    @CustomValidation
    @Email(groups = ValidationGroupOne.class,message = "{NotBlank.eleve.email}")
    private String email;

    @CustomValidation
    private Address address;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = InstantDeserializer.class)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = InstantDeserializer.class)
    private LocalDateTime modifyDate = LocalDateTime.now();

    @Nullable
    private MultipartFile avatarFile;

    @Nullable
    private Set<UserRole> roles;
}
