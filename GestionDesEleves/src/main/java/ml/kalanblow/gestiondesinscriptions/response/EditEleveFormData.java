package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;

import java.time.LocalDate;
import java.util.Base64;


/**
 * Cette classe représente les données d'édition d'un élève. Elle étend les fonctionnalités de la classe EditUserFormData
 * en ajoutant des propriétés spécifiques à un élève.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EditEleveFormData extends CreateEleveFormData {

    private Long id;
    private long version;
    private String password;
    private String passwordRepeated;
    private LocalDate dateDeNaissance;
    private Etablissement etablissement;
    private int age;
    private Parent pere;
    private Parent mere;
    private String ineNumber;
    private String avatarBase64Encoded;


    /**
     * Convertit un objet Eleve en un objet EditEleveFormData.
     *
     * @param eleve L'objet Eleve à convertir.
     * @return Un objet EditEleveFormData contenant les données de l'objet Eleve.
     */
    public static EditEleveFormData fromUser(Eleve eleve) {

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
        edit.setPhoneNumber(eleve.getPhoneNumber().asString());

        if (eleve.getAvatar() != null) {

            String encoded = Base64.getEncoder().encodeToString(eleve.getAvatar());

            edit.setAvatarBase64Encoded(encoded);
        }
        return edit;

    }

    /**
     * Convertit un objet EditEleveFormData en un objet EditEleveParameters.
     *
     * @return Un objet EditEleveParameters contenant les données de l'objet EditEleveFormData.
     */
    public EditEleveParameters toEleveParameters() {

        EditEleveParameters editEleveParameters = new EditEleveParameters();
        editEleveParameters.setAge(getAge());
        editEleveParameters.setEtablissement(getEtablissement());
        editEleveParameters.setDateDeNaissance(getDateDeNaissance());
        editEleveParameters.setVersion(getVersion());
        editEleveParameters.setEmail(new Email(getEmail()));
        editEleveParameters.setMaritalStatus(getMaritalStatus());
        editEleveParameters.setStudentIneNumber(getIneNumber());
        editEleveParameters.setPassword(getPassword());
        editEleveParameters.setGender(getGender());
        editEleveParameters.setAddress(getAddress());
        editEleveParameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        editEleveParameters.setCreatedDate(getCreatedDate());
        editEleveParameters.setModifyDate(getModifyDate());
        editEleveParameters.setPere(getPere());
        editEleveParameters.setMere(getMere());


        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {

            editEleveParameters.setAvatar(getAvatarFile());
        }

        return editEleveParameters;
    }
}
