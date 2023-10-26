package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Data
public class EditEtablissementScolaireFormData  {

    private Long id;
    private long version;
    private String nomEtablissement;
    private Address address;
    private Email email;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastModifiedDate = LocalDateTime.now();
    private PhoneNumber phoneNumber;
    private Set<Eleve> eleves;
    private Set<Enseignant> enseignants;
    private Set<Salle> salleDeClasses;
    private MultipartFile avatar;
    public static EditEtablissementScolaireFormData fromEtablissementScolaire(Etablissement etablissement) {


        EditEtablissementScolaireFormData editEtablissementScolaireFormData = new EditEtablissementScolaireFormData();
        editEtablissementScolaireFormData.setAddress(etablissement.getAddress());
        editEtablissementScolaireFormData.setNomEtablissement(etablissement.getNomEtablissement());
        editEtablissementScolaireFormData.setEnseignants(etablissement.getEnseignants());
        editEtablissementScolaireFormData.setEleves(etablissement.getEleves());
        editEtablissementScolaireFormData.setEmail(etablissement.getEmail());
        editEtablissementScolaireFormData.setCreatedDate(etablissement.getCreatedDate());
        editEtablissementScolaireFormData.setSalleDeClasses(etablissement.getSalles());
        editEtablissementScolaireFormData.setPhoneNumber(etablissement.getPhoneNumber());
        editEtablissementScolaireFormData.setLastModifiedDate(etablissement.getLastModifiedDate());
        editEtablissementScolaireFormData.setId(etablissement.getEtablisementScolaireId());
        editEtablissementScolaireFormData.setVersion(etablissement.getVersion());

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
        editEtablissementScolaireParameters.setVersion(version);
        return editEtablissementScolaireParameters;
    }
}