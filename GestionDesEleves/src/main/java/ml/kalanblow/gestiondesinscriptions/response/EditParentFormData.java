package ml.kalanblow.gestiondesinscriptions.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.request.EditParentParameters;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public non-sealed class EditParentFormData extends CreateParentFormData {

    private Long id;
    private long version;
    private String profession;
    private Set<Eleve> enfantsPere = new HashSet<>();

    private Set<Eleve> enfantsMere = new HashSet<>();

    public static EditParentFormData fromParent(Parent parent) {

        EditParentFormData editParentFormData = new EditParentFormData();
        editParentFormData.setVersion(editParentFormData.version);
        editParentFormData.setId(editParentFormData.getId());
        editParentFormData.setAddress(parent.getAddress());
        editParentFormData.setEmail(parent.getEmail().asString());
        editParentFormData.setGender(parent.getGender());
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

      EditParentParameters editParentParameters= new EditParentParameters();
      editParentParameters.setVersion(version);

      editParentParameters.setProfession(getProfession());

      editParentParameters.setEnfantsPere(enfantsPere);

      editParentParameters.setEnfantsMere(enfantsMere);

        return  editParentParameters;
    }
}
