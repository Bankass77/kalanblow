package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AdministrateurAlreadyExistsException extends RuntimeException{


    public AdministrateurAlreadyExistsException(String message) {
        super(message);
    }
}