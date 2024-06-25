package ml.kalanblow.gestiondesinscriptions.infrastructure;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ServletUriComponentsBuilderWrapper {

    public static ServletUriComponentsBuilder fromCurrentRequest(){
        return ServletUriComponentsBuilder.fromCurrentRequest();
    }

}
