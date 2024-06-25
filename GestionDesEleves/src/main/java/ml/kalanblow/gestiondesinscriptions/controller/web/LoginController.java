package ml.kalanblow.gestiondesinscriptions.controller.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetails userDetails) { //<.>
        if (userDetails == null) {
            log.info("User details is null: {}", userDetails);
            return "login";
        } else {
            log.info("User details is not null: {}", userDetails);
            return "redirect:/";
        }
    }


}
