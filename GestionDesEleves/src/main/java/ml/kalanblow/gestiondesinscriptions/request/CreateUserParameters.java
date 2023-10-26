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
public sealed class CreateUserParameters permits CreateEleveParameters, CreateEnseignantParameters, CreateParentParameters,  EditUserParameters {

    
    private UserName userName;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Email email;

    @Nullable
    private String password;

    private PhoneNumber phoneNumber;

    private Address address;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime modifyDate = LocalDateTime.now();


    @Nullable
    private MultipartFile avatar;

}
