package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public non-sealed class EditEnseignantParameters extends CreateEnseignantParameters {

    private Long version;


  public void  updateEnseignant( Enseignant enseignant){

        enseignant.setCoursDEnseignements(getCoursDEnseignements());
        enseignant.setAge(getAge());
        enseignant.setDateDeNaissance(getDateDeNaissance());
        enseignant.setHoraireClasses(getHoraireClasses());
        enseignant.setEtablissementScolaire(getEtablissementScolaire());
        enseignant.setJoursDisponibles(getJoursDisponibles());
        enseignant.setLeMatricule(getLeMatricule());
        enseignant.setAddress(getAddress());
        enseignant.setHeureDebutDisponibilite(getHeureDebutDisponibilite());
        enseignant.setHeureFinDisponibilite(getHeureFinDisponibilite());
        enseignant.setEtablissementScolaire(getEtablissementScolaire());
        enseignant.setCreatedDate(getCreatedDate());
        enseignant.setEmail(getEmail());
        enseignant.setGender(getGender());
        enseignant.setUserName(getUserName());
        enseignant.setRoles(getRoles());
        enseignant.setPhoneNumber(getPhoneNumber());
        enseignant.setMaritalStatus(getMaritalStatus());
        enseignant.setLastModifiedDate(getModifyDate());

      MultipartFile avatar = getAvatar();

      if (avatar != null) {
          try {
              enseignant.setAvatar(avatar.getBytes());
          } catch (IOException e) {

              throw new KaladewnManagementException().throwException(EntityType.ELEVE,
                      ExceptionType.DUPLICATE_ENTITY, String.valueOf(enseignant.getId()));
          }

      }
  }
}
