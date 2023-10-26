package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public final class EditParentParameters extends CreateUserParameters {

    private final long version;

    private String profession;

    private Set<Eleve> enfantsPere = new HashSet<>();

    private Set<Eleve> enfantsMere = new HashSet<>();

    public EditParentParameters(long version, UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                                PhoneNumber phoneNumber, Address address, String profession, Set<Eleve> enfantsPere, Set<Eleve> enfantsMere ,LocalDateTime createdDate, LocalDateTime modifyDate) {

        this.profession = profession;
        this.version = version;
        this.enfantsMere=enfantsMere;
        this.enfantsPere=enfantsPere;
    }

    // tag::update
    public void update(Parent parent) {

        parent.setGender(getGender());
        parent.setAddress(getAddress());
        parent.setProfession(profession);
        parent.setMaritalStatus(getMaritalStatus());
        parent.setCreatedDate(getCreatedDate());
        parent.setLastModifiedDate(getModifyDate());
        parent.setPhoneNumber(getPhoneNumber());
        parent.setPassword(getPassword());
        parent.setUserName(getUserName());
        parent.setEnfantsMere(getEnfantsMere());
        parent.setEnfantsPere(getEnfantsPere());
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

}
