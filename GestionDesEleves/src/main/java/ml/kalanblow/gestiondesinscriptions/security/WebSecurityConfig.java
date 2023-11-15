package ml.kalanblow.gestiondesinscriptions.security;


import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.security.UserAuthenticationErrorHandler;
import ml.kalanblow.gestiondesinscriptions.exception.security.UserForbiddenErrorHandler;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static ml.kalanblow.gestiondesinscriptions.security.WebSecurityConfig.EleveConfigurationAdapter.getSecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig {


    // end::class-annotations[]
    private final UserDetailsService userDetailsService;

    private final  BCryptPasswordEncoder passwordEncoder;


    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error/409";
    public static final String DEFAULT_SUCCESS_URL = "/kalanden";

    public static final String[] ENDPOINTS_WHITELIST = {
            "/css/**",
            "/",
            "/login",
            "/home",
            "/svg/*",
            "/api/integration-test/**"
    };

    public WebSecurityConfig(UserDetailsService userDetailsService,
                             BCryptPasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.passwordEncoder=passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    // end::configure-users[]

    // tag::configure-http[]

    @Configuration
    @Order(1)
    public static class EleveConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainEleve(HttpSecurity http) throws Exception {

            return getSecurityFilterChain(http);
        }



     protected  static SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

            http.csrf(httpSecurityCsrfConfigurer ->
                    httpSecurityCsrfConfigurer.ignoringRequestMatchers(ENDPOINTS_WHITELIST));
            http.authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                            .requestMatchers("/kalanden/eleve/ajouter").hasRole(UserRole.ADMIN.getValue())
                            .requestMatchers("/kalanden/eleve/*)")
                            .hasRole(UserRole.ADMIN.getValue())
                            .requestMatchers("/kalanden/eleve/*/delete")
                            .hasRole(UserRole.ADMIN.getValue())
                            .anyRequest().authenticated())
                    .exceptionHandling(exception ->exception.authenticationEntryPoint(userAuthenticationErrorHandler())
                            .accessDeniedHandler(new UserForbiddenErrorHandler()))
                    .formLogin(

                            form -> form
                                    .loginPage(LOGIN_URL)
                                    .loginProcessingUrl(LOGIN_URL)
                                    .successHandler(new KalanblowLoginSuccessHandler())
                                    .failureUrl(LOGIN_FAIL_URL)
                                    .failureHandler(authenticationFailureHandler())
                                    .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                                    .permitAll()

                    ).logout(
                            logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL)
                                    ).logoutSuccessUrl(LOGOUT_URL+ "?logout").permitAll())/*.invalidateHttpSession(true).deleteCookies("JSESSIONID")
                                    .logoutSuccessUrl(LOGOUT_URL+ "?logout")).sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                            .invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))*/;


            return http.build();
        }


        // end::configure-http[]

    }

    @Configuration
    @Order(2)
    public static class EnseignantConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainEnseignant(HttpSecurity http) throws Exception {
            return getSecurityFilterChain(http);
        }

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static AuthenticationEntryPoint userAuthenticationErrorHandler() {
        UserAuthenticationErrorHandler userAuthenticationErrorHandler =
                new UserAuthenticationErrorHandler();
        userAuthenticationErrorHandler.setRealmName("Basic Authentication");
        return userAuthenticationErrorHandler;
    }


    @Bean
    public static AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler("/login?error=true");
    }

}
