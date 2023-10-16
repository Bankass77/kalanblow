package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.EtablissementScolaire;
import ml.kalanblow.gestiondesinscriptions.request.CreateEtablissementScolaireParameters;
import ml.kalanblow.gestiondesinscriptions.request.EditEtablissementScolaireParameters;

public interface EtablissementScolaireService {


    EtablissementScolaire creerEtablissementScolaire(CreateEtablissementScolaireParameters createEtablissementScolaireParameters);

    EtablissementScolaire miseAJourEtablissementScolaire(long id, EditEtablissementScolaireParameters editEtablissementScolaireParameters);

    EtablissementScolaire trouverEtablissementScolaireParSonNom( String nom);

    EtablissementScolaire trouverEtablissementScolaireParSonIdentifiant( long id);
}
