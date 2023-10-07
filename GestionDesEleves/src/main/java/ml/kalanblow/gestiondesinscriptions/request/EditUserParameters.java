package ml.kalanblow.gestiondesinscriptions.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import java.util.Set;
@Data
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public non-sealed class EditUserParameters extends CreateUserParameters {

    private final long version;

    public EditUserParameters(long version, UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                              PhoneNumber phoneNumber, Address address, LocalDateTime createdDate, LocalDateTime modifyDate,
                              Set<UserRole> roles) {
        super(userName, gender, maritalStatus, email, null, phoneNumber, address, createdDate, modifyDate, roles);

        this.version = version;
    }

    // tag::update
    public void update(User user) {
        user.setCreatedDate(getCreatedDate());
        user.setEmail(getEmail());
        user.setGender(getGender());
        user.setLastModifiedDate(getCreatedDate());
        user.setPhoneNumber(getPhoneNumber());
        user.setMaritalStatus(getMaritalStatus());
        user.setAddress(getAddress());
        user.setUserName(getUserName());
        user.setVersion(user.getVersion());
        user.setRoles(getRoles());

        MultipartFile avatar = getAvatar();
        if (avatar != null) {

            try {
                user.setAvatar(avatar.getBytes());
            } catch (IOException e) {

                throw new KaladewnManagementException().throwException(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND,
                        e.getMessage());
            }
        }

    }

    // end::update[]
}
