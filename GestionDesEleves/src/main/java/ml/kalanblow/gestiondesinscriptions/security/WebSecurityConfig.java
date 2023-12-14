package ml.kalanblow.gestiondesinscriptions.security;


import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.exception.security.UserForbiddenErrorHandler;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtAuthenticationFilter;
import ml.kalanblow.gestiondesinscriptions.security.jwt.KalanblowUnAuthorizedJWTAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig {

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final KalanblowUnAuthorizedJWTAuthenticationEntryPoint kalanblowUnAuthorizedJwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error/409";
    public static final String DEFAULT_SUCCESS_URL = "/";
    protected static final String[] ENDPOINTS_WHITELIST = {
            "/api/integration-test/**",
            LOGIN_URL,
            "/css/**",
            "/js/**",
            "/api/v1/kalanden",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/kalanden/auth",
            "/kalanden",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"


    };

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService,
                             BCryptPasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter,
                             KalanblowUnAuthorizedJWTAuthenticationEntryPoint kalanblowUnAuthorizedJwtAuthenticationEntryPoint,CustomAccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.kalanblowUnAuthorizedJwtAuthenticationEntryPoint = kalanblowUnAuthorizedJwtAuthenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;

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


    @Order(1)
    public class EleveConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainEleve(HttpSecurity http) throws Exception {

            return getSecurityEleveFilterChain(http);
        }

        protected  SecurityFilterChain getSecurityEleveFilterChain(HttpSecurity http) throws Exception {

            http.csrf(httpSecurityCsrfConfigurer ->
                    httpSecurityCsrfConfigurer.ignoringRequestMatchers(ENDPOINTS_WHITELIST).csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

            http.authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                            .requestMatchers("/kalanden/eleve/ajouter").hasRole(UserRole.ADMIN.getValue())
                            .requestMatchers("/kalanden/eleve/*").hasRole(UserRole.ADMIN.getValue())
                            .requestMatchers("/kalanden/eleve/*/delete").hasRole(UserRole.ADMIN.getValue())
                            .anyRequest().authenticated())
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(kalanblowUnAuthorizedJwtAuthenticationEntryPoint)
                            .accessDeniedHandler(accessDeniedHandler))
                    .formLogin(
                            form -> form
                                    .loginPage(LOGIN_URL)
                                    .loginProcessingUrl(LOGIN_URL)
                                    .successHandler(new KalanblowLoginSuccessHandler())
                                    .failureUrl(LOGIN_FAIL_URL)
                                    .failureHandler(authenticationFailureHandler())
                                    .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                                    .permitAll()
                    )
                    .logout(
                            logout -> logout
                                    .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                                    .logoutSuccessUrl(LOGOUT_URL + "?logout")
                                    .permitAll())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        // end::configure-http[]

    }


    @Order(2)
    public class EnseignantConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainEnseignant(HttpSecurity http) throws Exception {
            return getSecurityEnseignantFilterChain(http);
        }

        protected  SecurityFilterChain getSecurityEnseignantFilterChain(HttpSecurity http) throws Exception {
            http.csrf(httpSecurityCsrfConfigurer ->
                    httpSecurityCsrfConfigurer.ignoringRequestMatchers(ENDPOINTS_WHITELIST).csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

            http.authorizeHttpRequests(authz -> authz
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                            .requestMatchers("/kalanden/enseignant/ajouter").hasRole(UserRole.TEACHER.getValue())
                            .requestMatchers("/kalanden/enseignant/*").hasRole(UserRole.TEACHER.getValue())
                            .requestMatchers("/kalanden/enseignant/*/delete").hasRole(UserRole.TEACHER.getValue())
                            .anyRequest().authenticated())
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(kalanblowUnAuthorizedJwtAuthenticationEntryPoint)
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
                    )
                    .logout(
                            logout -> logout
                                    .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
                                    .logoutSuccessUrl(LOGOUT_URL + "?logout")
                                    .permitAll())
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(true));
            http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginFailureHandler("/login?error=true");
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        config.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

        // should be set order to -100 because we need to CorsFilter before SpringSecurityFilter
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }
}
