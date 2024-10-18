package ml.kalanblow.gestiondescours.exception;

import java.io.Serializable;

public enum ExceptionType implements Serializable {

    ENTITY_NOT_FOUND("not.found"), DUPLICATE_ENTITY("duplicate"), ENTITY_EXCEPTION("exception");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }

}
