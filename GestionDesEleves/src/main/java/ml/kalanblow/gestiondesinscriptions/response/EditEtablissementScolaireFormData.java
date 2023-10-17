package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;

@NoArgsConstructor
@Data
public class EditEtablissementScolaireFormData extends CreateEtablissementScolaireFormData {

    public static EditEtablissementScolaireFormData fromEtablissementScolaire(EtablissementScolaire etablissementScolaire) {


        EditEtablissementScolaireFormData editEtablissementScolaireFormData = new EditEtablissementScolaireFormData();
        editEtablissementScolaireFormData.setAddress(etablissementScolaire.getAddress());
        etablissementScolaire.setNomEtablissement(etablissementScolaire.getNomEtablissement());
        etablissementScolaire.setEnseignants(etablissementScolaire.getEnseignants());
        etablissementScolaire.setAvatar(etablissementScolaire.getAvatar());
        etablissementScolaire.setEleves(etablissementScolaire.getEleves());
        etablissementScolaire.setEmail(etablissementScolaire.getEmail());
        etablissementScolaire.setCreatedDate(etablissementScolaire.getCreatedDate());
        etablissementScolaire.setSalleDeClasses(etablissementScolaire.getSalleDeClasses());
        etablissementScolaire.setPhoneNumber(etablissementScolaire.getPhoneNumber());
        etablissementScolaire.setLastModifiedDate(etablissementScolaire.getLastModifiedDate());

        return editEtablissementScolaireFormData;
    }


    public EditEtablissementScolaireParameters toEtablissementScolaire() {

        EditEtablissementScolaireParameters editEtablissementScolaireParameters = new EditEtablissementScolaireParameters();
        editEtablissementScolaireParameters.setSalleDeClasses(getSalleDeClasses());
        editEtablissementScolaireParameters.setAddress(getAddress());
        editEtablissementScolaireParameters.setNomEtablissement(getNomEtablissement());
        editEtablissementScolaireParameters.setEleves(getEleves());
        editEtablissementScolaireParameters.setAvatar(getAvatar());
        editEtablissementScolaireParameters.setEmail(getEmail());
        editEtablissementScolaireParameters.setCreatedDate(getCreatedDate());
        editEtablissementScolaireParameters.setLastModifiedDate(getLastModifiedDate());
        editEtablissementScolaireParameters.setEnseignants(getEnseignants());
        editEtablissementScolaireParameters.setPhoneNumber(getPhoneNumber());
        return editEtablissementScolaireParameters;
    }
}
