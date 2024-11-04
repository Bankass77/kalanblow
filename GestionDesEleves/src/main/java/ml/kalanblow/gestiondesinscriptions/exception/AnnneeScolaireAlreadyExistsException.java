package ml.kalanblow.gestiondesinscriptions.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AnnneeScolaireAlreadyExistsException  extends  RuntimeException{

    public  AnnneeScolaireAlreadyExistsException (String message){
        super(message);
    }
}
