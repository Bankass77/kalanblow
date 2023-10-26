package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditParentParameters;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class EditParentFormData extends EditUserFormData {

    private Long id;
    private long version;
    private String profession;
    private Set<Eleve> enfantsPere = new HashSet<>();

    private Set<Eleve> enfantsMere = new HashSet<>();

    public static EditParentFormData fromParentFormDat(Parent parent) {

        EditParentFormData editParentFormData = new EditParentFormData();
        editParentFormData.setVersion(editParentFormData.version);
        editParentFormData.setId(editParentFormData.getId());
        editParentFormData.setAddress(parent.getAddress());
        editParentFormData.setEmail(parent.getEmail().asString());
        editParentFormData.setGender(parent.getGender());
        editParentFormData.setAvatarBase64Encoded(editParentFormData.getAvatarBase64Encoded());
        editParentFormData.setCreatedDate(parent.getCreatedDate());
        editParentFormData.setMaritalStatus(parent.getMaritalStatus());
        editParentFormData.setModifyDate(parent.getLastModifiedDate());
        editParentFormData.setRoles(parent.getRoles());
        editParentFormData.setNomDeFamille(parent.getUserName().getNomDeFamille());
        editParentFormData.setPrenom(parent.getUserName().getPrenom());
        editParentFormData.setEnfantsPere(parent.getEnfantsPere());
        editParentFormData.setEnfantsMere(parent.getEnfantsMere());
        editParentFormData.setProfession(editParentFormData.getProfession());


        return editParentFormData;
    }


    public EditParentParameters toParentParameters() {


        EditParentParameters editParentParameters = new EditParentParameters(version, new UserName(getPrenom(),
                getNomDeFamille()), getGender(), getMaritalStatus(), new Email(getEmail()),
                new PhoneNumber(getPhoneNumber()), getAddress(), profession,enfantsPere, enfantsMere  ,getCreatedDate(), getModifyDate());

        return  editParentParameters;
    }
}
