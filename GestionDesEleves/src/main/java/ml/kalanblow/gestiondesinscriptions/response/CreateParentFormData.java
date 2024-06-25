package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public sealed class CreateParentFormData extends AbstractUserFormData permits EditParentFormData {

    private String profession;

    public CreateParentParameters toParentParameters() {

        CreateParentParameters createParentParameters = new CreateParentParameters();
        createParentParameters.setProfession(profession);

        createParentParameters.setAvatar(getAvatarFile());

        return createParentParameters;
    }
}
