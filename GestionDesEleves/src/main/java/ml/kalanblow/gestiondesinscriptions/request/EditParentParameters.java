package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public final class EditParentParameters extends CreateParentParameters {

    private long version;

    private String profession;

    private Set<Eleve> enfantsPere = new HashSet<>();

    private Set<Eleve> enfantsMere = new HashSet<>();

    // tag::update
    public void update(Parent parent) {

        parent.setGender(getGender());
        parent.setAddress(getAddress());
        parent.setProfession(profession);
        parent.setMaritalStatus(getMaritalStatus());
        parent.setCreatedDate(getCreatedDate());
        parent.setLastModifiedDate(getModifyDate());
        parent.setPhoneNumber(getPhoneNumber());
        parent.setUserName(getUserName());
        // Assuming each collection has only one element, if not, handle accordingly
        if (!getEnfantsMere().isEmpty()) {
            Eleve mereEleve = getEnfantsMere().iterator().next();
            parent.addEnfantPere(mereEleve);
        }
        if (!getEnfantsPere().isEmpty()) {
            Eleve pereEleve = getEnfantsPere().iterator().next();
            parent.addEnfantMere(pereEleve);
        }
        parent.setRoles(Collections.singleton(UserRole.USER));

        MultipartFile avatar = getAvatar();
        if (avatar != null) {

            try {
                parent.setAvatar(avatar.getBytes());
            } catch (IOException e) {

                throw new KaladewnManagementException().throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND,
                        e.getMessage());
            }
        }

    }
    // end::update[]


    // tag::remove
    public void remove(Parent parent) {
        // Remove all enfantsPere from the parent
        parent.getEnfantsPere().forEach(eleve -> parent.removeEnfantPere(eleve));

        // Remove all enfantsMere from the parent
        parent.getEnfantsMere().forEach(eleve -> parent.removeEnfantMere(eleve));
    }

    // end::remove[]
}
