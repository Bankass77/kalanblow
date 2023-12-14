package ml.kalanblow.gestiondesinscriptions.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TokenType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Slf4j
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private static final String  AUTHORIZATION_PREFIX = "Authorization";
    private static  final  String BASIC_PREFIX= "Basic";


    @Autowired
    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;


    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Afficher les cookies de la requête
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("Cookie: {}={}", cookie.getName(), cookie.getValue());
            }
        }
        // try to get JWT in cookie or in Authorization Header
        String jwt = jwtHelper.getJwtFromCookies(request);
        final String authHeader = request.getHeader(AUTHORIZATION_PREFIX);
        log.info("Authorization Header: {}", authHeader);
        if((jwt == null && (authHeader ==  null || !authHeader.startsWith(TokenType.BEARER.name()))) || request.getRequestURI().contains("/kalanden")){
            filterChain.doFilter(request, response);
            return;
        }


        if (isBasicAuth(request)) {
            // Gérer l'authentification Basic Auth
            filterChain.doFilter(request, response);
            return;
        }
      // Si l'authentification basique échoue, continuer avec l'authentification JWT
        String jwtToken = extractJwtToken(request);
        if((jwtToken == null && (authHeader ==  null || !authHeader.startsWith(TokenType.BEARER.name()))) || request.getRequestURI().contains("/kalanden")){
            filterChain.doFilter(request, response);
            return;
        }

        String username = null;
        String token = null;
        if (jwt != null ) {
            token = authHeader.substring(7);

            try {
                username = jwtHelper.getUserNameFromToken(token);
                if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication()==null) {
                    log.info("Authentification réussie !!");
                    try {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
                        if (validateToken) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            log.info("Token non valide !!");
                        }
                    } catch (Exception e) {
                        log.info("Erreur lors de l'authentification !!", e);

                    }
                }
                filterChain.doFilter(request, response);

            }
            catch (IllegalArgumentException e) {

                log.info("Argument illégal lors de la récupération du nom d'utilisateur !!", e);

            }catch (ExpiredJwtException e){
                log.info("Token expiré !!", e);

            }catch (MalformedJwtException e){
                log.info("Token mal formé !!",  e);

            }catch (Exception e){
                log.info("Erreur lors de la récupération du nom d'utilisateur !!", e);
            }
        }else {
            log.info("Token non trouvé !!");
        }



    }

    /**
     * Vérifie si l'authentification de la requête est de type Basic Auth.
     *
     * @param request La requête HTTP à inspecter.
     * @return true si l'authentification est de type Basic Auth, sinon false.
     */
    private boolean isBasicAuth(HttpServletRequest request) {
        String jwtBasicAuth = jwtHelper.getJwtFromCookies(request);
        // Récupérer l'en-tête Authorization de la requête
        String authHeader = request.getHeader(AUTHORIZATION_PREFIX);
        // Vérifier si l'en-tête Authorization est présent et commence par "Basic "
        return jwtBasicAuth !=null && authHeader != null && authHeader.startsWith(BASIC_PREFIX);
    }
    private String extractJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_PREFIX);
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(TokenType.BEARER.name())) {
            return authHeader.substring(7); // Retirer le préfixe "Bearer "
        }
        return null;
    }
}
