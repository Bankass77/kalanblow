package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public final class EditEleveParameters extends CreateEleveParameters {

    private Long version;

    public EditEleveParameters(long version, UserName userName, Gender gender, MaritalStatus maritalStatus,
                               Email email, String password, PhoneNumber phoneNumber, Address address, LocalDateTime createdDate,
                               LocalDateTime modifyDate, LocalDate dateDeNaissance, int age, String studentIneNumber, String motherFirstName,
                               String motherLastName, PhoneNumber motherMobile, String fatherLastName, String fatherFirstName,
                               PhoneNumber fatherMobile, Set<Role> roles, EtablissementScolaire etablissementScolaire) {
        super(userName, gender, maritalStatus, email, password, phoneNumber, address, createdDate, modifyDate, dateDeNaissance,
                age, studentIneNumber, motherFirstName, motherLastName, motherMobile, fatherLastName, fatherFirstName,
                fatherMobile, roles, etablissementScolaire);

        this.version = version;

    }

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
