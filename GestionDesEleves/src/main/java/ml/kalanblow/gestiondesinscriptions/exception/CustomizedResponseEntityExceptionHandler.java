package ml.kalanblow.gestiondesinscriptions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ml.kalanblow.gestiondesinscriptions.dto.response.Response;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(KaladewnManagementException.EntityNotFoundException.class)
    public final ResponseEntity handleNotFoundExceptions(Exception ex , WebRequest request){

        Response response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return  new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(KaladewnManagementException.DuplicateEntityException.class)
    public final ResponseEntity handleNotFoundException1(Exception ex, WebRequest webRequest){
        Response response = Response.duplicateEntity();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return  new ResponseEntity(response, HttpStatus.CONFLICT);
    }
}
