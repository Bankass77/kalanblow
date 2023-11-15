package ml.kalanblow.gestiondesinscriptions.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public LoginFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
    }

}
