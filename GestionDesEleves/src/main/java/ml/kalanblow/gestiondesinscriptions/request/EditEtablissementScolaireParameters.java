package ml.kalanblow.gestiondesinscriptions.request;


import ml.kalanblow.gestiondesinscriptions.exception.EntityType;
import ml.kalanblow.gestiondesinscriptions.exception.ExceptionType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

public class EditEtablissementScolaireParameters extends CreateEtablissementScolaireParameters {


    public EditEtablissementScolaireParameters(String nomEtablissement, Address address, Email email, LocalDateTime createdDate,
                                               LocalDateTime lastModifiedDate, PhoneNumber phoneNumber,
                                               Set<Eleve> eleves, Set<Enseignant> enseignants, Set<SalleDeClasse> salleDeClasses,
                                               MultipartFile avatar) {

    }

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
