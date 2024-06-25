package ml.kalanblow.gestiondesinscriptions.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.constraint.NotExistingUser;
import ml.kalanblow.gestiondesinscriptions.constraint.PasswordsMatch;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;
import ml.kalanblow.gestiondesinscriptions.validation.ValidationGroupTwo;

import java.time.LocalDate;
import java.util.Base64;

/**
 * Cette classe représente les données d'édition d'un élève. Elle étend les fonctionnalités de la classe EditUserFormData en ajoutant des propriétés spécifiques
 * à un élève.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@PasswordsMatch(groups = ValidationGroupTwo.class)
@NotExistingUser(groups = ValidationGroupTwo.class)
public final class EditEleveFormData extends AbstractUserFormData {


    private Long id;
    private long version;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit être fort.")
    private String passwordRepeated;
    private LocalDate dateDeNaissance;
    private Etablissement etablissement;
    private int age;
    private Parent pere;
    private Parent mere;
    private String ineNumber;
    private String avatarBase64Encoded;


    public static EditEleveFormData fromEleve(Eleve eleve) {
        EditEleveFormData edit = new EditEleveFormData();
        edit.setId(eleve.getId());
        edit.setVersion(eleve.getVersion());
        edit.setPrenom(eleve.getUserName().getPrenom());
        edit.setNomDeFamille(eleve.getUserName().getNomDeFamille());
        edit.setModifyDate(eleve.getLastModifiedDate());
        edit.setCreatedDate(eleve.getCreatedDate());
        edit.setAddress(eleve.getAddress());
        edit.setEmail(eleve.getEmail().asString());
        edit.setGender(eleve.getGender());
        edit.setPassword(eleve.getPassword());
        edit.setPasswordRepeated(eleve.getPassword());
        edit.setRoles(eleve.getRoles());
        edit.setDateDeNaissance(eleve.getDateDeNaissance());
        edit.setEtablissement(eleve.getEtablissement());
        edit.setMaritalStatus(eleve.getMaritalStatus());
        if(eleve.getPhoneNumber() != null) {
            edit.setPhoneNumber(eleve.getPhoneNumber().asString());
        }
        edit.setAge(eleve.getAge());
        edit.setPere(eleve.getPere());
        edit.setMere(eleve.getMere());
        edit.setIneNumber(eleve.getIneNumber());
        if (eleve.getAvatar() != null) {
            String encoded = Base64.getEncoder().encodeToString(eleve.getAvatar());
            edit.setAvatarBase64Encoded(encoded);
        }
        return edit;
    }

    public EditEleveParameters toEleveParameters() {
        EditEleveParameters parameters = new EditEleveParameters();
        parameters.setAge(getAge());
        parameters.setEtablissement(getEtablissement());
        parameters.setDateDeNaissance(getDateDeNaissance());
        parameters.setVersion(getVersion());
        parameters.setEmail(new Email(getEmail()));
        parameters.setMaritalStatus(getMaritalStatus());
        parameters.setStudentIneNumber(getIneNumber());
        parameters.setPassword(getPassword());
        parameters.setGender(getGender());
        parameters.setAddress(getAddress());
        parameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        parameters.setCreatedDate(getCreatedDate());
        parameters.setModifyDate(getModifyDate());
        parameters.setPere(getPere());
        parameters.setMere(getMere());
        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }
        return parameters;
    }
}
