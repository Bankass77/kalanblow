package ml.kalanblow.gestiondesinscriptions.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@NoArgsConstructor
@Data
public class EditEtablissementScolaireParameters extends CreateEtablissementScolaireParameters {


    public void updateEtablissementScolaire(EtablissementScolaire etablissementScolaire) {

        etablissementScolaire.setNomEtablissement(getNomEtablissement());
        etablissementScolaire.setEleves(getEleves());
        etablissementScolaire.setAddress(getAddress());
        etablissementScolaire.setEmail(getEmail());
        etablissementScolaire.setEnseignants(getEnseignants());
        etablissementScolaire.setCreatedDate(getCreatedDate());
        etablissementScolaire.setPhoneNumber(getPhoneNumber());
        etablissementScolaire.setLastModifiedDate(getLastModifiedDate());
        etablissementScolaire.setSalleDeClasses(getSalleDeClasses());

        MultipartFile avatar = getAvatar();

        if (avatar != null) {
            try {
                etablissementScolaire.setAvatar(avatar.getBytes());
            } catch (IOException e) {

                throw new KaladewnManagementException().throwException(EntityType.ELEVE,
                        ExceptionType.DUPLICATE_ENTITY, String.valueOf(etablissementScolaire.getEtablisementScolaireId()));
            }

        }

    }
}
