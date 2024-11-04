package ml.kalanblow.gestiondesinscriptions.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String format(String template, Object... args) {
        return messageSource.getMessage(template, args, Locale.getDefault());
    }
}
