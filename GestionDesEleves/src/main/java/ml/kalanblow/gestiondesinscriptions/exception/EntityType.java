package ml.kalanblow.gestiondesinscriptions.exception;

import java.io.Serializable;

public enum EntityType implements Serializable {
    PHONENUMBER, EMAIL, USER, ELEVE, PARENT, ETABLISSEMENTSCOLAIRE, ENSEIGNANT, ABSENCE, ANNEESCOLAIRE,
    APPELDEPRESENCE, COURSDENSEIGNEMENT, HORAIREDECLASSE, MATIERE, PERIODEVACANCES, SALLEDECLASSE;
}
