package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateParentParameters;

@Data
@NoArgsConstructor
public class CreateParentFormData extends CreateUserFormData  {

    private String profession;

    public CreateParentParameters toParentParameters() {

        CreateParentParameters createParentParameters = new CreateParentParameters();
        createParentParameters.setProfession(profession);
        createParentParameters.setAddress(getAddress());
        createParentParameters.setGender(getGender());
        createParentParameters.setEmail(new Email(getEmail()));
        createParentParameters.setMaritalStatus(getMaritalStatus());
        createParentParameters.setModifyDate(getModifyDate());
        createParentParameters.setAvatar(getAvatarFile());
        createParentParameters.setCreatedDate(getCreatedDate());
        createParentParameters.setPassword(getPassword());
        createParentParameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        createParentParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));

        return createParentParameters;
    }
}
