package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = false)
public non-sealed class EditUserParameters extends CreateUserParameters {

    private final long version;

    public EditUserParameters(long version, UserName userName, Gender gender, MaritalStatus maritalStatus, Email email,
                              PhoneNumber phoneNumber, Address address, LocalDateTime createdDate, LocalDateTime modifyDate
                             ) {
        super();

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
        user.setVersion(version);
        user.setPassword(getPassword());

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
