package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.model.Address;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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
                                LocalDateTime modifyDate) {
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
    }
}
