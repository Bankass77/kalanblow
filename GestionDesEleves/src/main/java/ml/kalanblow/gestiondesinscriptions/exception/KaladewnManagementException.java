package ml.kalanblow.gestiondesinscriptions.exception;

import ml.kalanblow.gestiondesinscriptions.enums.UserRole;

public class KaladewnManagementException extends RuntimeException {

    public KaladewnManagementException(String message) {
        super(message);
    }

    public  KaladewnManagementException(Long id, UserRole userRole){
        super(userRole + " with id: " + id + " not found");
    }
}
