package ml.kalanblow.gestiondesinscriptions.response;


import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementParameters;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Data
public class EditEtablissementFormData extends  CreateEtablissementFormData{

    private Long id;
    private long version;
    private String nomEtablissement;
    private Address address;

    @Valid
    private Email email;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    @Valid
    private PhoneNumber phoneNumber;
    private Set<Eleve> eleves;
    private Set<Enseignant> enseignants;
    private Set<Salle> salleDeClasses;
    private MultipartFile avatar;
    public static EditEtablissementFormData fromEtablissementScolaire(Etablissement etablissement) {


        EditEtablissementFormData editEtablissementScolaireFormData = new EditEtablissementFormData();
        editEtablissementScolaireFormData.setAddress(etablissement.getAddress());
        editEtablissementScolaireFormData.setNomEtablissement(etablissement.getNomEtablissement());
        editEtablissementScolaireFormData.setEnseignants(etablissement.getEnseignants());
        editEtablissementScolaireFormData.setEleves(etablissement.getEleves());
        editEtablissementScolaireFormData.setEmail(etablissement.getEmail());
        editEtablissementScolaireFormData.setSalleDeClasses(etablissement.getSalles());
        editEtablissementScolaireFormData.setPhoneNumber(etablissement.getPhoneNumber());
        editEtablissementScolaireFormData.setLastModifiedDate(etablissement.getLastModifiedDate());
        editEtablissementScolaireFormData.setId(etablissement.getEtablisementScolaireId());
        editEtablissementScolaireFormData.setVersion(etablissement.getVersion());

        return editEtablissementScolaireFormData;
    }
    public EditEtablissementParameters toEtablissementScolaire() {

        EditEtablissementParameters editEtablissementScolaireParameters = new EditEtablissementParameters();
        editEtablissementScolaireParameters.setSalleDeClasses(getSalleDeClasses());
        editEtablissementScolaireParameters.setAddress(getAddress());
        editEtablissementScolaireParameters.setNomEtablissement(getNomEtablissement());
        editEtablissementScolaireParameters.setEleves(getEleves());
        editEtablissementScolaireParameters.setAvatar(getAvatar());
        editEtablissementScolaireParameters.setEmail(getEmail());
        editEtablissementScolaireParameters.setLastModifiedDate(getLastModifiedDate());
        editEtablissementScolaireParameters.setEnseignants(getEnseignants());
        editEtablissementScolaireParameters.setPhoneNumber(getPhoneNumber());
        editEtablissementScolaireParameters.setVersion(version);
        return editEtablissementScolaireParameters;
    }
}
