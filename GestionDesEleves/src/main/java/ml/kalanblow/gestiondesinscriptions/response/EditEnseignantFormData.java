package ml.kalanblow.gestiondesinscriptions.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.Email;
import ml.kalanblow.gestiondesinscriptions.model.Enseignant;
import ml.kalanblow.gestiondesinscriptions.model.UserName;
import ml.kalanblow.gestiondesinscriptions.request.CreateEnseignantParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEnseignantParameters;

@NoArgsConstructor
@Data
public class EditEnseignantFormData extends CreateEnseignantFormData {

    public static EditEnseignantFormData formEnseignantFormData(Enseignant enseignant) {

        EditEnseignantFormData editEnseignantFormData = new EditEnseignantFormData();
        editEnseignantFormData.setCoursDEnseignements(enseignant.getCoursDEnseignements());
        editEnseignantFormData.setAge(enseignant.getAge());
        editEnseignantFormData.setDateDeNaissance(enseignant.getDateDeNaissance());
        editEnseignantFormData.setEtablissementScolaire(enseignant.getEtablissementScolaire());
        editEnseignantFormData.setHoraireClasses(enseignant.getHoraireClasses());
        editEnseignantFormData.setAddress(enseignant.getAddress());
        editEnseignantFormData.setCreatedDate(enseignant.getCreatedDate());
        editEnseignantFormData.setEmail(enseignant.getEmail().asString());
        editEnseignantFormData.setGender(enseignant.getGender());
        editEnseignantFormData.setHeureDebutDisponibilite(enseignant.getHeureDebutDisponibilite());
        editEnseignantFormData.setHeureFinDisponibilite(enseignant.getHeureFinDisponibilite());
        editEnseignantFormData.setJoursDisponibles(enseignant.getJoursDisponibles());
        editEnseignantFormData.setLeMatricule(enseignant.getLeMatricule());
        editEnseignantFormData.setMaritalStatus(enseignant.getMaritalStatus());
        editEnseignantFormData.setAvatarFile(editEnseignantFormData.getAvatarFile());
        return editEnseignantFormData;


    }

    public EditEnseignantParameters toEnseignantParameters() {

        EditEnseignantParameters editEnseignantParameters = new EditEnseignantParameters();
        editEnseignantParameters.setAddress(getAddress());
        editEnseignantParameters.setAge(getAge());
        editEnseignantParameters.setCoursDEnseignements(getCoursDEnseignements());
        editEnseignantParameters.setCreatedDate(getCreatedDate());
        editEnseignantParameters.setDateDeNaissance(getDateDeNaissance());
        editEnseignantParameters.setEmail(new Email(getEmail()));
        editEnseignantParameters.setEtablissementScolaire(getEtablissementScolaire());
        editEnseignantParameters.setGender(getGender());
        editEnseignantParameters.setHeureDebutDisponibilite(getHeureDebutDisponibilite());
        editEnseignantParameters.setHoraireClasses(getHoraireClasses());
        editEnseignantParameters.setHeureFinDisponibilite(getHeureFinDisponibilite());
        editEnseignantParameters.setJoursDisponibles(getJoursDisponibles());
        editEnseignantParameters.setLeMatricule(getLeMatricule());
        editEnseignantParameters.setMaritalStatus(getMaritalStatus());
        editEnseignantParameters.setModifyDate(getModifyDate());
        editEnseignantParameters.setPassword(getPassword());
        editEnseignantParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        editEnseignantParameters.setRoles(getRoles());
        editEnseignantParameters.setAvatar(getAvatarFile());

        return editEnseignantParameters;

    }
}
