/*
package ml.kalanblow.gestiondesinscriptions.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ml.kalanblow.gestiondesinscriptions.config.MessageService;

@Component
public class ExceptionFactory {

    private final MessageService messageService;

    @Autowired
    public ExceptionFactory(MessageService messageService) {
        this.messageService = messageService;
    }

    // Exception pour une entité non trouvée
    public KaladewnManagementException createNotFoundException(EntityType entityType, String details) {
        String messageTemplate = entityType.name().toLowerCase() + ".not.found";
        String message = messageService.format(messageTemplate, details);
        return new KaladewnManagementException(message);
    }

    // Exception pour une duplication d'entité
    public KaladewnManagementException createDuplicateEntityException(EntityType entityType, String details) {
        String messageTemplate = entityType.name().toLowerCase() + ".duplicate";
        String message = messageService.format(messageTemplate, details);
        return new KaladewnManagementException(message);
    }

    // Exception pour une erreur d'authentification
    public KaladewnManagementException createAuthenticationException(EntityType entityType, String details) {
        String messageTemplate = entityType.name().toLowerCase() + ".authentication.failed";
        String message = messageService.format(messageTemplate, details);
        return new KaladewnManagementException(message);
    }

    // Exception pour une erreur d'autorisation (accès refusé)
    public KaladewnManagementException createAuthorizationException(EntityType entityType, String details) {
        String messageTemplate = entityType.name().toLowerCase() + ".access.denied";
        String message = messageService.format(messageTemplate, details);
        return new KaladewnManagementException(message);
    }

    // Exception pour une opération non valide (validation)
    public KaladewnManagementException createInvalidOperationException(EntityType entityType, String details) {
        String messageTemplate = entityType.name().toLowerCase() + ".invalid.operation";
        String message = messageService.format(messageTemplate, details);
        return new KaladewnManagementException(message);
    }

    // Exception pour un paramètre manquant ou invalide
    public KaladewnManagementException createMissingParameterException(String parameterName) {
        String messageTemplate = "parameter.missing";  // clé de message dans messages.properties
        String message = messageService.format(messageTemplate, parameterName);
        return new KaladewnManagementException(message);
    }
}

*/
