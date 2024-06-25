package ml.kalanblow.gestiondesinscriptions.security;

import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.security.UserAuthenticationErrorHandler;
import ml.kalanblow.gestiondesinscriptions.exception.security.UserForbiddenErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final KalanblowCustomUserDetailsService kalanblowCustomUserDetailsService;

    String myKey = "cd2ddaf45654b7bca955629e386dd14235cba96648589f4806b00436f6b709c6";

    public static final String LOGIN_URL = "/login";

    public static final String LOGOUT_URL = "/logout";

    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error/409";

    public static final String DEFAULT_SUCCESS_URL = "/kalanden";

    public static final String[] ENDPOINTS_WHITELIST = { "/css/**", "/", LOGIN_URL, "/home", "/svg/*", "/api/integration-test/**" };

    @Autowired
    public WebSecurityConfig(KalanblowCustomUserDetailsService kalanblowCustomUserDetailsService) {
        this.kalanblowCustomUserDetailsService = kalanblowCustomUserDetailsService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain eleveSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter,
            RememberMeServices rememberMeServices) throws Exception {
        http.authorizeHttpRequests(
                        authz -> authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                //TODO il faudra modifier le role à ADMIN
                                .requestMatchers("/kalanden/eleve/ajouter").hasRole(UserRole.STUDENT.getValue()).requestMatchers("/kalanden/eleve/*")
                                .hasRole(UserRole.STUDENT.getValue()).requestMatchers("/kalanden/eleve/*/delete").hasRole(UserRole.STUDENT.getValue()).anyRequest()
                                .authenticated()).exceptionHandling(
                        exception -> exception.authenticationEntryPoint(userAuthenticationErrorHandler()).accessDeniedHandler(new UserForbiddenErrorHandler()))
                .formLogin(form -> form.loginPage(LOGIN_URL).loginProcessingUrl(LOGIN_URL).successHandler(new KalanblowLoginSuccessHandler())
                        .failureUrl(LOGIN_FAIL_URL).failureHandler(authenticationFailureHandler()).defaultSuccessUrl(DEFAULT_SUCCESS_URL).permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL)).logoutSuccessUrl(LOGIN_URL + "?logout=true").permitAll()
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID"))/*.sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).invalidSessionUrl("/invalidSession").maximumSessions(1)
                                .maxSessionsPreventsLogin(true))*/.authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .rememberMe((remember) -> remember.rememberMeServices(rememberMeServices));

        return http.build();
    }

    // Creates a DaoAuthenticationProvider to handle user authentication
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(kalanblowCustomUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }

    // Defines a PasswordEncoder bean that uses bcrypt hashing by default for password encoding
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Defines an AuthenticationManager bean to manage authentication processes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static AuthenticationEntryPoint userAuthenticationErrorHandler() {
        UserAuthenticationErrorHandler userAuthenticationErrorHandler = new UserAuthenticationErrorHandler();
        userAuthenticationErrorHandler.setRealmName("Basic Authentication");
        return userAuthenticationErrorHandler;
    }

    @Bean
    public static AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler("/login?error=true");
    }

    @Value("${spring.websecurity.debug:false}")
    boolean webSecurityDebug;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(webSecurityDebug);
    }
}
