package ml.kalanblow.gestiondesinscriptions.service;

import ml.kalanblow.gestiondesinscriptions.model.Administrateur;

public interface AdminService {
    public Administrateur ajouterAdministrateur(Administrateur administrateur);

    public Administrateur authentifierAdministrateur(String email, String passoword);

    public void supprimerAdministrateur(long admin);

}
