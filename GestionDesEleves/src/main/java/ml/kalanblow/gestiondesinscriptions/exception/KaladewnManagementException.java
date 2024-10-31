package ml.kalanblow.gestiondesinscriptions.exception;


import lombok.Setter;
import ml.kalanblow.gestiondesinscriptions.config.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.Optional;

@Component
@ResponseStatus(HttpStatus.NOT_FOUND)
@Setter
public class KaladewnManagementException extends Exception {

    /**
     *
     */
    private  final long serialVersionUID = 1L;


    private final MessageService messageService;

    @Autowired
    public KaladewnManagementException(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    public RuntimeException throwException(String messageTemplate, String... args) {
        String formattedMessage = messageService.getMessage(messageTemplate, args);
        return new RuntimeException(formattedMessage);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType type de l'entité
     * @param exceptionType le type de l'exception
     * @param args le message de l'argument
     * @return {@link RuntimeException}
     */
    public RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType type de l'entité
     * @param exceptionType le type de l'exception
     * @param args le message de l'argument
     * @return {@link RuntimeException}
     */
    public  RuntimeException throwExceptionWithId(EntityType entityType, ExceptionType exceptionType, String id,
                                                        String... args) {
        String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType,
     * messageTemplate and args
     *
     * @param entityType type de l'entité
     * @param exceptionType le type de l'exception
     * @param args le message de l'argument
     * @return {@link RuntimeException}
     */
    public  RuntimeException throwExceptionWithTemplate(EntityType entityType, ExceptionType exceptionType,
                                                              String messageTemplate, String... args) {
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate type de message
     * @param exceptionType le type de l'exception
     * @param args le message de l'argument
     * @return {@link RuntimeException}
     */
    private  RuntimeException throwException(ExceptionType exceptionType, String messageTemplate,
                                                   String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(messageService.format(messageTemplate, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new DuplicateEntityException(messageService.format(messageTemplate, args));
        }
        return new RuntimeException(messageService.format(messageTemplate, args));
    }

    /**
     *
     * @param entityType type de l'entité
     * @param exceptionType le type de l'exception
     * @return un message avec le type de l'entité et le type d'exception
     */
    private  String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
        return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
    }


    // -------------------------------------------------------------------------
    // No String/variable interpolation in Java. Use format instead.
    // -------------------------------------------------------------------------
    public  class EntityNotFoundException extends RuntimeException {
        /**
         *
         */
        private  final long serialVersionUID = 1L;

        public EntityNotFoundException(String message) {
            super(message);
        }

        /**
         *
         * @param template du message
         * @param arg1  argument1 du message
         * @param arg2 argument2 du message
         * @param cause de l'exception
         */
        public EntityNotFoundException(String template, Object arg1, Object arg2, Throwable cause) {
            super(MessageFormat.format(template, arg1, arg2), cause);
        }
    }

    public  class DuplicateEntityException extends RuntimeException {
        /**
         *
         */
        private  final long serialVersionUID = 1L;

        public DuplicateEntityException(String message) {
            super(message);
        }
    }

}
