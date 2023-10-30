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
/**
 * Cette classe représente des données d'édition d'utilisateur. Elle étend les fonctionnalités de la classe AbstractUserFormData
 * en ajoutant des propriétés spécifiques à un utilisateur.
 */
public non-sealed class EditUserFormData extends AbstractUserFormData {

    private Long id;
    private String avatarBase64Encoded;
    private long version;

    /**
     * Convertit un objet User en une instance d'EditUserFormData.
     *
     * @param user L'objet User à convertir.
     * @return Une instance d'EditUserFormData contenant les données de l'objet User.
     */
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
        edit.setPhoneNumber(user.getPhoneNumber().asString());
        edit.setRoles(user.getRoles());
        edit.setVersion(user.getVersion());
        edit.setId(user.getId());

        if (user.getAvatar() != null) {

            String encoded = Base64.getEncoder().encodeToString(user.getAvatar());
            edit.setAvatarBase64Encoded(encoded);
        }

        return edit;

    }


    /**
     * Convertit cette instance d'EditUserFormData en une instance d'EditUserParameters.
     *
     * @return Une instance d'EditUserParameters contenant les données de cette instance d'EditUserFormData.
     */
    public EditUserParameters toUserParameters() {

        EditUserParameters parameters = new EditUserParameters(version, new UserName(getPrenom(), getNomDeFamille()),
                getGender(), getMaritalStatus(), new Email(getEmail()), new PhoneNumber(getPhoneNumber()),
                getAddress(), LocalDateTime.now(), LocalDateTime.now());
        parameters.setAddress(getAddress());
        parameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));


        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }

        return parameters;
    }
}
