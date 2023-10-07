package ml.kalanblow.gestiondesinscriptions.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Configuration class for the Kaladewn application. This class implements
 * the {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}
 * interface and defines various beans related to Thymeleaf template resolution,
 * template engines, request logging, and validation.
 */
@EnableWebMvc
@Configuration
public class KaladewnConfig implements WebMvcConfigurer {

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
     * Configures a Thymeleaf template resolver for HTML 5 templates.
     *
     * @return The Thymeleaf template resolver for HTML 5 templates.
     */
    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {

        var templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    /**
     * Configures a Thymeleaf template engine with Spring integration.
     *
     * @return The Thymeleaf template engine with Spring integration.
     */
    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {

        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

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

}
