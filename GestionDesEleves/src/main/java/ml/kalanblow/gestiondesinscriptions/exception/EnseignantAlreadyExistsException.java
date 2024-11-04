package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnseignantAlreadyExistsException extends  RuntimeException{

    public EnseignantAlreadyExistsException(final String message) {
        super(message);
    }
}
