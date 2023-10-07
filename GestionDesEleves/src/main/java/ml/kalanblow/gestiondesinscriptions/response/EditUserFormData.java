package ml.kalanblow.gestiondesinscriptions.response;

import lombok.*;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.PhoneNumber;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.EditUserParameters;


import java.time.LocalDateTime;
import java.util.Base64;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public non-sealed class EditUserFormData extends AbstractUserFormData {
    private Long id;
    private String avatarBase64Encoded;
    private long version;

    public static EditUserFormData fromUser(User user) {

        EditUserFormData edit = new EditUserFormData();

        edit.setAddress(user.getAddress());
        edit.setCreatedDate(user.getCreatedDate());
        edit.setEmail(user.getEmail().asString());
        edit.setPrenom(user.getUserName().getPrenom());
        edit.setNomDeFamille(user.getUserName().getNomDeFamille());
        edit.setGender(user.getGender());
        edit.setMaritalStatus(user.getMaritalStatus());
        edit.setModifyDate(user.getLastModifiedDate());
        edit.setPhoneNumber(user.getPhoneNumber());
        edit.setRoles(user.getRoles());

        edit.setVersion(user.getVersion());

        if (user.getAvatar() != null) {

            String encoded = Base64.getEncoder().encodeToString(user.getAvatar());
            edit.setAvatarBase64Encoded(encoded);
        }

        return edit;

    }

    public EditUserParameters toUserParameters() {

        EditUserParameters parameters = new EditUserParameters(version, new UserName(getPrenom(), getNomDeFamille()),
                getGender(), getMaritalStatus(), new Email(getEmail()), new PhoneNumber(getPhoneNumber().asString()),
                getAddress(), LocalDateTime.now(), LocalDateTime.now(), getRoles());

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }

        return parameters;
    }
}
