package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public sealed class CreateUserParameters permits CreateEleveParameters, CreateEnseignantParameters, EditUserParameters {

    private UserName userName;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Email email;
    private String password;
    private PhoneNumber phoneNumber;
    private Address address;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifyDate = LocalDateTime.now();
    private Set<Role> roles;

    @Nullable
    private MultipartFile avatar;

    /**
     *
     * @param userName
     * @param gender
     * @param maritalStatus
     * @param email
     * @param password
     * @param phoneNumber
     * @param address
     * @param createdDate
     * @param modifyDate
     * @param roles
     */
    public CreateUserParameters(UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                                String password, PhoneNumber phoneNumber, Address address, LocalDateTime createdDate,
                                LocalDateTime modifyDate, Set<Role> roles) {
        super();
        this.userName = userName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdDate = createdDate;
        this.modifyDate = modifyDate;
        this.roles = roles;
    }
}
