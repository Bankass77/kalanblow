package ml.kalanblow.gestiondesinscriptions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySources({@PropertySource(value = "classpath:i18n/messages.properties"),
        @PropertySource(value = "classpath:application.properties"), @PropertySource(value = "classpath:application.yaml")})

@Profile("dev")
public class KaladewnPropertiesConfig {

    @Autowired
    private Environment env;

    public Object getConfigValue(String configKey) {

        return env.getProperty(configKey);
    }
}
