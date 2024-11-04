package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EtablissementScolaireAlreadyExistsException extends  RuntimeException{
    public EtablissementScolaireAlreadyExistsException(final String message) {
        super(message);
    }
}
