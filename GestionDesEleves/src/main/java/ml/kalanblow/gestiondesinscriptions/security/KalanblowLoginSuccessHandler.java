package ml.kalanblow.gestiondesinscriptions.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KalanblowLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * Gère le succès de l'authentification en redirigeant l'utilisateur en fonction de son rôle.
     *
     * @param request
     *         La requête HTTP.
     * @param response
     *         La réponse HTTP.
     * @param authentication
     *         L'objet représentant l'authentification réussie.
     * @throws ServletException
     *         En cas d'erreur lors de la gestion de la servlet.
     * @throws IOException
     *         En cas d'erreur d'entrée/sortie lors de la gestion de la redirection.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        KalanblowUserDetails kalanblowUserDetails = (KalanblowUserDetails) authentication.getPrincipal();

        String contextPath = request.getContextPath();

        // TODO le lien de redirection doit être modifier
        if(isStudent(kalanblowUserDetails) && !isTeacher(kalanblowUserDetails)) {

            // TO DO le lien de redirection doit être modifier par le dashbord elève
            contextPath += "/kalanden";

        }
        getRedirectStrategy().sendRedirect(request, response, contextPath);
    }

    /**
     * Vérifie si l'utilisateur est un étudiant.
     *
     * @param userDetails
     *         Les détails de l'utilisateur.
     * @return {@code true} si l'utilisateur est un étudiant, sinon {@code false}.
     */
    private boolean isStudent(KalanblowUserDetails userDetails) {
        return userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(UserRole.STUDENT.getValue()));
    }

    /**
     * Vérifie si l'utilisateur est un enseignant.
     *
     * @param userDetails
     *         Les détails de l'utilisateur.
     * @return {@code true} si l'utilisateur est un enseignant, sinon {@code false}.
     */
    private boolean isTeacher(KalanblowUserDetails userDetails) {
        return userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equalsIgnoreCase(UserRole.TEACHER.getValue()));
    }
}
