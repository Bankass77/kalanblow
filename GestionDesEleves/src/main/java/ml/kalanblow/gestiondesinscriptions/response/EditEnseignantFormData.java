package ml.kalanblow.gestiondesinscriptions.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEnseignantParameters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

@NoArgsConstructor
@Data
public class EditEnseignantFormData extends CreateEnseignantFormData {
    private Long id;
    private long version;
    private String leMatricule;
    private LocalDate dateDeNaissance;
    private Etablissement etablissement;
    private int age;
    private List<Cours> coursDEnseignements;
    private LocalTime heureDebutDisponibilite;
    private List<DayOfWeek> joursDisponibles;
    private List<Horaire> horaireClasses;
    private LocalTime heureFinDisponibilite;
    private String password;
    private String passwordRepeated;
    private String avatarBase64Encoded;

    public static EditEnseignantFormData formEnseignant(Enseignant enseignant) {

        EditEnseignantFormData editEnseignantFormData = new EditEnseignantFormData();
        editEnseignantFormData.setCoursDEnseignements(enseignant.getCoursDEnseignements());
        editEnseignantFormData.setAge(enseignant.getAge());
        editEnseignantFormData.setDateDeNaissance(enseignant.getDateDeNaissance());
        editEnseignantFormData.setEtablissement(enseignant.getEtablissement());
        editEnseignantFormData.setHoraireClasses(enseignant.getDisponibilites());
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
        editEnseignantFormData.setId(enseignant.getId());
        editEnseignantFormData.setVersion(enseignant.getVersion());
        editEnseignantFormData.setPassword(enseignant.getPassword());
        editEnseignantFormData.setPasswordRepeated(enseignant.getPassword());

        if(enseignant.getAvatar() !=null){

            String encoded= Base64.getEncoder().encodeToString(enseignant.getAvatar());

            editEnseignantFormData.setAvatarBase64Encoded(encoded);
        }
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
        editEnseignantParameters.setEtablissement(getEtablissement());
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

        if(getAvatarFile() !=null && !getAvatarFile().isEmpty()){
            editEnseignantParameters.setAvatar(getAvatarFile());
        }

        return editEnseignantParameters;

    }
}
