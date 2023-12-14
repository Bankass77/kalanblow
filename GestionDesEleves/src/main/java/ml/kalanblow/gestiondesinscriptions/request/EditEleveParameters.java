package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Eleve;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public final class EditEleveParameters extends CreateEleveParameters {

    private long version;

    // tag::update[]
    public void updateStudent(Eleve eleve) {

        eleve.setUserName(new UserName(getUserName().getPrenom(), getUserName().getNomDeFamille()));
        eleve.setEmail(getEmail());
        eleve.setPhoneNumber(getPhoneNumber());
        eleve.setAddress(getAddress());
        eleve.setDateDeNaissance(getDateDeNaissance());
        eleve.setGender(getGender());
        eleve.setMaritalStatus(getMaritalStatus());
        eleve.setIneNumber(getStudentIneNumber());
        eleve.setAge(getAge());
        eleve.setLastModifiedDate(LocalDateTime.now());
        eleve.setPere(getPere());
        eleve.setMere(getMere());
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

    public void applyEleveVesrionModifications(EditEleveParameters parameters) {
        // Apply modifications...
        // Increment version even if no significant changes are made.
        this.version++;
    }

}
