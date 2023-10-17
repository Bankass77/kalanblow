package ml.kalanblow.gestiondesinscriptions.request;


import lombok.*;

import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public final class EditEleveParameters extends CreateEleveParameters {

    private Long version;


    // tag::update[]
    public void updateStudent(Eleve eleve) {

        eleve.setUserName(getUserName());
        eleve.setEmail(getEmail());
        eleve.setPhoneNumber(getPhoneNumber());
        eleve.setAddress(getAddress());
        eleve.setDateDeNaissance(getDateDeNaissance());
        eleve.setGender(getGender());
        eleve.setMaritalStatus(getMaritalStatus());
        eleve.setIneNumber(getStudentIneNumber());
        eleve.setAge(getAge());
        eleve.setEtablissementScolaire(getEtablissementScolaire());
        eleve.setCreatedDate(LocalDateTime.now());
        eleve.setLastModifiedDate(LocalDateTime.now());
        eleve.setAbsences(getAbsences());
        eleve.setFatherFirstName(getFatherFirstName());
        eleve.setFatherLastName(getFatherLastName());

        eleve.setMotherFirstName(getMotherFirstName());
        eleve.setMotherLastName(getMotherLastName());

        eleve.setRoles(getRoles());

        MultipartFile avatar = getAvatar();

        if (avatar != null) {
            try {
                eleve.setAvatar(avatar.getBytes());
            } catch (IOException e) {

                throw new KaladewnManagementException().throwException(EntityType.ELEVE,
                        ExceptionType.DUPLICATE_ENTITY, String.valueOf(eleve.getId()));
            }

        }

    }
    // end::update[]
}
