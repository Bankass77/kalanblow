package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;

public interface EtablissementScolaireService {


    EtablissementScolaire creerEtablissementScolaire(EtablissementScolaire etablissementScolaire);

    EtablissementScolaire miseAJourEtablissementScolaire(long id, EtablissementScolaire etablissementScolaire);

    EtablissementScolaire trouverEtablissementScolaireParSonNom( String nom);

    EtablissementScolaire trouverEtablissementScolaireParSonIdentifiant( long id);
}
