package ml.kalanblow.gestiondesinscriptions.config;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private final KaladewnPropertiesConfig propertiesConfig;

    @Autowired
    public MessageService(KaladewnPropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }


    public String getMessage(String template, String... args) {
        String messageTemplate = propertiesConfig.getConfigValue(template).toString();
        return MessageFormat.format(messageTemplate, (Object[]) args);
    }


    /**
     *
     * @param template le template du message
     * @param args le message de l'argument
     * @return un message avec un format
     */
    public String format(String template, String... args) {
        // Récupère le contenu du template dans propertiesConfig
        Optional<Object> templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template));

        // Retourne le message formaté ou un message par défaut
        return templateContent
                .map(o -> MessageFormat.format((String) o, (Object[]) args))
                .orElseGet(() -> String.format(template, (Object[]) args));
    }

}
