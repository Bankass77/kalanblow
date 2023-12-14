package ml.kalanblow.gestiondesinscriptions.response;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEleveParameters;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


/**
 * Cette classe représente les données d'édition d'un élève. Elle étend les fonctionnalités de la classe EditUserFormData
 * en ajoutant des propriétés spécifiques à un élève.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public non-sealed class EditEleveFormData extends AbstractUserFormData {

    private Long id;

    private long version;

    private String avatarBase64Encoded;

    private String ineNumber;

    private LocalDate dateDeNaissance;

    private int age;

    private Parent pere;

    private Parent mere;

    private Etablissement etablissement;

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
           edit.setIneNumber(eleve.getIneNumber());
           edit.setAddress(eleve.getAddress());
           edit.setEmail(eleve.getEmail().asString());
           edit.setGender(eleve.getGender());
           edit.setMaritalStatus(eleve.getMaritalStatus());
           edit.setRoles(eleve.getRoles());
           edit.setDateDeNaissance(eleve.getDateDeNaissance());
           edit.setAge(eleve.getAge());
           edit.setEtablissement(eleve.getEtablissement());
           edit.setPhoneNumber(eleve.getPhoneNumberAsString());
           edit.setModifyDate(eleve.getLastModifiedDate());
           edit.setPere(eleve.getPere());
           edit.setMere(eleve.getMere());

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
        editEleveParameters.setAge(age);
        editEleveParameters.setUserName(new UserName(getPrenom(), getNomDeFamille()));
        editEleveParameters.setEtablissement(etablissement);
        editEleveParameters.setDateDeNaissance(dateDeNaissance);
        editEleveParameters.setVersion(getVersion());
        editEleveParameters.setEmail(new Email(getEmail()));
        editEleveParameters.setMaritalStatus(getMaritalStatus());
        editEleveParameters.setStudentIneNumber(ineNumber);
        editEleveParameters.setGender(getGender());
        editEleveParameters.setAddress(getAddress());
        editEleveParameters.setPhoneNumber(new PhoneNumber(getPhoneNumber()));
        editEleveParameters.setModifyDate(getModifyDate());
        editEleveParameters.setPere(pere);
        editEleveParameters.setMere(mere);

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {

            editEleveParameters.setAvatar(getAvatarFile());
        }

        return editEleveParameters;
    }
}
