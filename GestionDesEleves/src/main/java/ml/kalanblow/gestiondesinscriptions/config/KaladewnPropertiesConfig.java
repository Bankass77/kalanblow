package ml.kalanblow.gestiondesinscriptions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
//@Profile("dev")
@PropertySource("classpath:i18n/messages.properties")
public class KaladewnPropertiesConfig {

    @Autowired
    private Environment env;

    public Object getConfigValue(String configKey) {

        return env.getProperty(configKey);
    }
}
