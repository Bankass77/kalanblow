package ml.kalanblow.gestiondesinscriptions.security.jwt;

import ml.kalanblow.gestiondesinscriptions.request.AuthenticationRequest;
import ml.kalanblow.gestiondesinscriptions.request.RegisterRequest;
import ml.kalanblow.gestiondesinscriptions.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
