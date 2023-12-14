package ml.kalanblow.gestiondesinscriptions.controller.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            // Utilisateur authentifié, redirigez vers la page d'accueil ou effectuez d'autres actions nécessaires
            return "redirect:/";
        } else {
            // Utilisateur non authentifié, affichez la page de connexion
            return "login";
        }
    }
}
