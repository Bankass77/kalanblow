package ml.kalanblow.gestiondesinscriptions.controller.api;

import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.model.JwtResponse;
import ml.kalanblow.gestiondesinscriptions.model.User;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kalanden/auth")
@Slf4j
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final  AuthenticationManager manager;
    private final JwtHelper helper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(UserDetailsService userDetailsService, AuthenticationManager manager, JwtHelper helper) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.helper = helper;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User request) {
        this.doAuthenticate(request.getEmail().asString(),passwordEncoder.encode( request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail().asString());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, passwordEncoder.encode(password));
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
