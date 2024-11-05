package ml.kalanblow.gestiondesinscriptions.exception.handler;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.exception.DuplicateEntityException;
import ml.kalanblow.gestiondesinscriptions.exception.EntityNotFoundException;
import ml.kalanblow.gestiondesinscriptions.exception.ErrorResponse;

@ControllerAdvice
@Slf4j
public class KalanblowGlobalExceptionHandler {

    MessageSource messageSource;
    public KalanblowGlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleException(NoHandlerFoundException ex, Locale locale) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(), LocalDateTime.now());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(ConstraintViolationException ex, Locale locale) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(e -> e.getMessage())
                .reduce(messageSource.getMessage("errors.found", null, locale), String::concat);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MethodArgumentNotValidException ex, Locale locale) {
        String message = ex.getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .reduce(messageSource.getMessage("errors.found", null, locale), String::concat);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MissingServletRequestParameterException ex, Locale locale) {
        String message = messageSource.getMessage("exception.requiredParam", new String[]{ex.getParameterName()}, locale);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, LocalDateTime.now());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleException(EntityNotFoundException ex, Locale locale) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                messageSource.getMessage("entity.notFound", new Object[]{ ex.getTheClassName(), ex.getId()} , locale),
                LocalDateTime.now());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Object> handleDuplicateEntityException(DuplicateEntityException ex, WebRequest request) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleException(MethodArgumentTypeMismatchException ex, Locale locale) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("exception.mismatch", new Object[] {ex.getValue(),ex.getRequiredType().getSimpleName()}, locale),
                LocalDateTime.now());
    }
    @ExceptionHandler(HttpStatusCodeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorResponse handleException(HttpClientErrorException.Unauthorized ex, Locale locale) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("exception.mismatch", null, locale),
                LocalDateTime.now());
    }

}
