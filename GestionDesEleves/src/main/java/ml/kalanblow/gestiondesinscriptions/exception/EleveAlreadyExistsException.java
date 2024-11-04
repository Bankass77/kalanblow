package ml.kalanblow.gestiondesinscriptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EleveAlreadyExistsException  extends  RuntimeException{

    public  EleveAlreadyExistsException (String message){
        super(message);
    }
}
