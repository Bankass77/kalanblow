package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChefEtablissementAlreadyExistsException  extends  RuntimeException{

    public ChefEtablissementAlreadyExistsException(final String message) {
        super(message);
    }
}
