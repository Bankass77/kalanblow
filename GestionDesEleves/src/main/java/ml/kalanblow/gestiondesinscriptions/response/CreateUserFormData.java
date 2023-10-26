package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateUserParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;


@Data
@EqualsAndHashCode(callSuper = false)
@PasswordsMatch(groups = ValidationGroupTwo.class)
@NotExistingUser(groups = ValidationGroupTwo.class)
public non-sealed class CreateUserFormData extends AbstractUserFormData {

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String passwordRepeated;

    public CreateUserParameters toUserParameters() {

        CreateUserParameters parameters = new CreateUserParameters(new UserName(getPrenom(), getNomDeFamille()),
                getGender(), getMaritalStatus(), new Email(getEmail()), password,
                new PhoneNumber(getPhoneNumber()), getAddress(), getCreatedDate(), getModifyDate());

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }

        return parameters;
    }
}
