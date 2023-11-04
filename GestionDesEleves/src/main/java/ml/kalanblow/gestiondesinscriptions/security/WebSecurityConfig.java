package ml.kalanblow.gestiondesinscriptions.security;


import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    // end::class-annotations[]
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(PasswordEncoder passwordEncoder,
                             UserDetailsService userDetailsService) { //<.>
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
    public static class  EleveConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainEleve(HttpSecurity http) throws Exception {

            return getSecurityFilterChain(http);
        }

        @Configuration
        @Order(2)
        public static class EnseignantConfigurationAdapter {

            @Bean
            public SecurityFilterChain filterChainEnseignant(HttpSecurity http) throws Exception {
                return getSecurityFilterChain(http);
            }

        }
        private static SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

            http.csrf(httpSecurityCsrfConfigurer ->
                    httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/integration-test/**")); //<.>
            http.authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers("/api/integration-test/**").permitAll()
                            .requestMatchers("/svg/*").permitAll()
                            .requestMatchers("/kalanden/eleve/ajouter").hasRole(UserRole.ADMIN.getValue()).requestMatchers("/kalanden/eleve/{id}(id=${eleve.id})").hasRole(UserRole.ADMIN.getValue())
                            . requestMatchers("/kalanden/eleve/'+${eleve.id}+'/delete'").hasRole(UserRole.ADMIN.getValue())
                            .anyRequest().authenticated())
                    .formLogin(

                            form -> form
                                    .loginPage("/login")
                                    .permitAll()

                    ).logout(
                            logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                    );


            return http.build();
        }
        // end::configure-http[]

    }


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
