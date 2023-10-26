package ml.kalanblow.gestiondesinscriptions.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.Etablissement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@NoArgsConstructor
@Data
public class EditEtablissementParameters extends CreateEtablissementParameters {

    private  long version;
    public void updateEtablissementScolaire(Etablissement etablissement) {

        etablissement.setNomEtablissement(getNomEtablissement());
        etablissement.setEleves(getEleves());
        etablissement.setAddress(getAddress());
        etablissement.setEmail(getEmail());
        etablissement.setEnseignants(getEnseignants());
        etablissement.setCreatedDate(getCreatedDate());
        etablissement.setPhoneNumber(getPhoneNumber());
        etablissement.setLastModifiedDate(getLastModifiedDate());
        etablissement.setSalles(getSalleDeClasses());

        MultipartFile avatar = getAvatar();

        if (avatar != null) {
            try {
                etablissement.setAvatar(avatar.getBytes());
            } catch (IOException e) {

                throw new KaladewnManagementException().throwException(EntityType.ELEVE,
                        ExceptionType.DUPLICATE_ENTITY, String.valueOf(etablissement.getEtablisementScolaireId()));
            }

        }

    }
}
