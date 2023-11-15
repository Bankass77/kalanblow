package ml.kalanblow.gestiondesinscriptions.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "{notnull.message}")
    private UserName userName;

    @NotNull(message = "{notnull.message}")
    private Gender gender;

    @NotNull(message = "{notnull.message}")
    private MaritalStatus maritalStatus;

    @NotNull(message = "{notnull.message}")
    private Email email;

    @NotNull(message = "{notnull.message}")
    private String password;

    @NotNull(message = "{notnull.message}")
    private PhoneNumber phoneNumber;

    @NotNull(message = "{notnull.message}")
    private Address address;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifyDate = LocalDateTime.now();


    @Nullable
    private MultipartFile avatar;

}
