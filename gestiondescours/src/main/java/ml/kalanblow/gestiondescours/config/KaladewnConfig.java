package ml.kalanblow.gestiondescours.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class KaladewnConfig {

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
     * Configures a local validator factory bean with a specified message source.
     *
     * @param messageSource The message source for validation messages.
     * @return The local validator factory bean.
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {

        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);

        return bean;
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

        return new OpenAPI().info(new Info().title(apiTitle).description(apiDescription)
                .version(apiVersion).contact(new Contact().name(apiContactName).url(apiContactUrl))
                .termsOfService(apiTermsOfService).license(new License().name(apiLicense).url(apiLicenseUrl))).externalDocs(new ExternalDocumentation().description(apiDescription).url(apiExternalDocUrl));

    }

    /**
     * @return
     */
    @Bean
    public HttpExchangeRepository httpTraceRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource(".env"));
        return configurer;
    }

}

