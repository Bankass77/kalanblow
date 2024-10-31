package ml.kalanblow.gestiondesinscriptions.service;

import java.util.Set;

import ml.kalanblow.gestiondesinscriptions.model.Administrateur;

 public interface AdminService {
     Administrateur ajouterAdministrateur(Administrateur administrateur);

     Administrateur authentifierAdministrateur(String email, String passoword);
     
     void supprimerAdministrateur(long admin);

     Set<Administrateur> getAllAdministrateurs ();

     Administrateur updateAdministrateur(long id, Administrateur adminDetails);

}
