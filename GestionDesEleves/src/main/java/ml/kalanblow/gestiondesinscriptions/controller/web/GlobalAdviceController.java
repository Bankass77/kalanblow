package ml.kalanblow.gestiondesinscriptions.controller.web;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalAdviceController {


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            DataIntegrityViolationException.class, ObjectOptimisticLockingFailureException.class
    })
    public ModelAndView handlerConflict(HttpServletRequest request, Exception e) {

        ModelAndView modelAndView = new ModelAndView("/error/409");
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }
}
