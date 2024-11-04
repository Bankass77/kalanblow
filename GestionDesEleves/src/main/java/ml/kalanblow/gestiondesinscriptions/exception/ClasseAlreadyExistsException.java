package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClasseAlreadyExistsException extends  RuntimeException{
    public ClasseAlreadyExistsException(final String message) {
        super(message);
    }

    public ClasseAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }


}
