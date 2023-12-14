package ml.kalanblow.gestiondesinscriptions.service;

import jakarta.servlet.http.HttpServletRequest;
import ml.kalanblow.gestiondesinscriptions.model.RefreshToken;
import ml.kalanblow.gestiondesinscriptions.request.RefreshTokenRequest;
import ml.kalanblow.gestiondesinscriptions.response.RefreshTokenResponse;
import org.springframework.http.ResponseCookie;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();
}
