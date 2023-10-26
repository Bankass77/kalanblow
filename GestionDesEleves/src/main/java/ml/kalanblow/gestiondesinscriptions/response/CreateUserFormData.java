package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;


@Data
@EqualsAndHashCode(callSuper = false)
@PasswordsMatch(groups = ValidationGroupTwo.class)
public non-sealed class CreateUserFormData extends AbstractUserFormData {

    @NotBlank(message = "{NotBlank.eleve.password}")
    private String password;


    @NotBlank(message = "{NotBlank.eleve.passwordRepeated}")
    private String passwordRepeated;


}
