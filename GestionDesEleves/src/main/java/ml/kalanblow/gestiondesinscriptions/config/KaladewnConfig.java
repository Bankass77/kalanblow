package ml.kalanblow.gestiondesinscriptions.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Configuration class for the Kaladewn application. This class implements the {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}
 * interface and defines various beans related to Thymeleaf template resolution, template engines, request logging, and validation.
 */

@Configuration
public class KaladewnConfig implements WebMvcConfigurer {

    @Value("${api.common.version}")
    String apiVersion;

    @Value("${api.common.title}")
    String apiTitle;

    @Value("${api.common.description}")
    String apiDescription;

    @Value("${api.common.termsOfService}")
    String apiTermsOfService;

    @Value("${api.common.licence}")
    String apiLicense;

    @Value("${api.common.licenseUrl}")
    String apiLicenseUrl;

    @Value("${api.common.externalDocDesc}")
    String apiExternalDocDesc;

    @Value("${api.common.externalDocUrl}")
    String apiExternalDocUrl;

    @Value("${api.common.contact.name}")
    String apiContactName;

    @Value("${api.common.contact.url}")
    String apiContactUrl;

    @Value("${api.common.contact.email}")
    String apiContactEmail;

    /**
     * Configures a Thymeleaf template resolver for SVG templates.
     *
     * @return The Thymeleaf template resolver for SVG templates.
     */
    @Bean
    @Qualifier("svgTemplateEngine")
    public ITemplateResolver svgTemplateResolver() {
        var resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:templates/svg/");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setSuffix(".svg");
        resolver.setTemplateMode("XML");
        resolver.setCacheable(false);
        resolver.setCheckExistence(true);

        return resolver;
    }

    /**
     * Configures a local validator factory bean with a specified message source.
     *
     * @param messageSource
     *         The message source for validation messages.
     * @return The local validator factory bean.
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_MANAGER and ROLE_MANAGER > ROLE_TEACHER and ROLE_TEACHER > ROLE_STUDENT and ROLE_MANAGER > ROLE_USER and ROLE_PARENT > ROLE_USER");
        return roleHierarchy;
    }

    /**
     * Configures a CommonsRequestLoggingFilter for request logging.
     *
     * @return The CommonsRequestLoggingFilter for request logging.
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        loggingFilter.setIncludeHeaders(false);
        loggingFilter.setAfterMessagePrefix("REQUEST DATA : ");
        return loggingFilter;
    }

    /**
     * @return new OpenAPI
     */
    @Bean
    public OpenAPI getOpenApiDocumentation() {

        return new OpenAPI().info(
                        new Info().title(apiTitle).description(apiDescription).version(apiVersion).contact(new Contact().name(apiContactName).url(apiContactUrl))
                                .termsOfService(apiTermsOfService).license(new License().name(apiLicense).url(apiLicenseUrl)))
                .externalDocs(new ExternalDocumentation().description(apiDescription).url(apiExternalDocUrl));

    }

    /**
     * @return new RestTemple
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * @return
     */
    @Bean
    public HttpExchangeRepository httpTraceRepository() {
        return new InMemoryHttpExchangeRepository();

    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource(".env"));
        return configurer;
    }

}

