package ml.kalanblow.gestiondesinscriptions.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
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
public sealed class AbstractUserFormData permits CreateUserFormData, EditUserFormData {

    @NotBlank
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String prenom;

    @NotBlank
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String nomDeFamille;

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @Pattern(regexp = "^(\\+223|00223)?[67]\\d{7}$")
    private String phoneNumber;

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private UserRole userRole;

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private Gender gender;

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private MaritalStatus maritalStatus;

    @NotBlank
    @Email(groups = ValidationGroupOne.class)
    private String email;

     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    private Address address;

   
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = InstantDeserializer.class)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
     @NotNull(message = "{notnull.message}", groups = ValidationGroupOne.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonDeserialize(using = InstantDeserializer.class)
    private LocalDateTime modifyDate = LocalDateTime.now();

    @Nullable
    private MultipartFile avatarFile;

    @Nullable
    private Set<UserRole> roles;
}
