package ml.kalanblow.gestiondesinscriptions.controller.web;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerAdvice {

    public ModelAndView handleConflict(HttpServletRequest request, Exception e){

        ModelAndView modelAndView= new ModelAndView("error/409");
        modelAndView.addObject("url", request.getRequestURL());

        return  modelAndView;
    }
}
