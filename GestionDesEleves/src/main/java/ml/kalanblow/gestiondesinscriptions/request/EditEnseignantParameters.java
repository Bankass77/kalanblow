package ml.kalanblow.gestiondesinscriptions.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = false)
public non-sealed class EditEnseignantParameters extends CreateEnseignantParameters {

private long version;
  public void  updateEnseignant( Enseignant enseignant){

        enseignant.setCoursDEnseignements(getCoursDEnseignements());
        enseignant.setAge(getAge());
        enseignant.setDateDeNaissance(getDateDeNaissance());
        enseignant.setDisponibilites(getHoraireClasses());
        enseignant.setEtablissement(getEtablissement());
        enseignant.setJoursDisponibles(getJoursDisponibles());
        enseignant.setLeMatricule(getLeMatricule());
        enseignant.setAddress(getAddress());
        enseignant.setHeureDebutDisponibilite(getHeureDebutDisponibilite());
        enseignant.setHeureFinDisponibilite(getHeureFinDisponibilite());
        enseignant.setEtablissement(getEtablissement());
        enseignant.setEmail(getEmail());
        enseignant.setGender(getGender());
        enseignant.setUserName(getUserName());
        enseignant.setPhoneNumber(getPhoneNumber());
        enseignant.setMaritalStatus(getMaritalStatus());
        enseignant.setLastModifiedDate(getModifyDate());
        enseignant.setVersion(getVersion());

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
