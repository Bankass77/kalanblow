package ml.kalanblow.gestiondesinscriptions.exception.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties({"stackTrace", "type", "title", "message", "localizedMessage", "parameters"})
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends KaladewnManagementException {

      private   HttpStatus status;
      private String detail;

    public static CommonException unauthorized() {
        return new CommonException(UNAUTHORIZED, "Unauthorised or Bad Credentials");
    }

    public static CommonException forbidden() {
        return new CommonException(FORBIDDEN, "Forbidden");
    }

    public static CommonException headerError() {
        return new CommonException(FORBIDDEN, "Missing Header");
    }

}
