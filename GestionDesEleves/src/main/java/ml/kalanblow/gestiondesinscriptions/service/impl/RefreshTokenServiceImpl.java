package ml.kalanblow.gestiondesinscriptions.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.kalanblow.gestiondesinscriptions.enums.TokenType;
import ml.kalanblow.gestiondesinscriptions.exception.KaladewnManagementException;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.repository.RefreshTokenRepository;
import ml.kalanblow.gestiondesinscriptions.repository.UserBaseRepository;
import ml.kalanblow.gestiondesinscriptions.request.RefreshTokenRequest;
import ml.kalanblow.gestiondesinscriptions.response.RefreshTokenResponse;
import ml.kalanblow.gestiondesinscriptions.security.jwt.JwtHelper;
import ml.kalanblow.gestiondesinscriptions.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class RefreshTokenServiceImpl  implements RefreshTokenService {

    private final UserBaseRepository<Enseignant> enseignantRepository;
    private final UserBaseRepository<Eleve> eleveRepository;
    private final UserBaseRepository<Parent> parentRepository;
    private  RefreshTokenRepository refreshTokenRepository;
    private  JwtHelper jwtService;

    @Autowired
    public RefreshTokenServiceImpl(UserBaseRepository<Enseignant> enseignantRepository, UserBaseRepository<Eleve> eleveRepository,
                                   UserBaseRepository<Parent> parentRepository, RefreshTokenRepository refreshTokenRepository) {
        this.enseignantRepository = enseignantRepository;
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.refresh-token.cookie-name}")
    private String refreshTokenName;

    public RefreshTokenServiceImpl(UserBaseRepository<Enseignant> enseignantRepository,
                                   UserBaseRepository<Eleve> eleveRepository,
                                   UserBaseRepository<Parent> parentRepository,
                                   RefreshTokenRepository refreshTokenRepository, JwtHelper jwtService) {
        this.enseignantRepository = enseignantRepository;
        this.eleveRepository = eleveRepository;
        this.parentRepository = parentRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        try {
            log.info("Fetching user with id: {}", userId);

            // Determine the type of user based on the repository
            User user = determineUserType(userId);

            log.info("Creating refresh token for user: {}", user);

            RefreshToken refreshToken = RefreshToken.builder()
                    .revoked(false)
                    .user(user)
                    .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
                    .expiryDate(Instant.now().plusMillis(refreshExpiration))
                    .build();
            return refreshTokenRepository.save(refreshToken);
        } catch (Throwable e) {
            throw new KaladewnManagementException.TokenException(null, e.getMessage());
        }
    }

    private User determineUserType(Long userId) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(userId);
        if (enseignant.isPresent()) {
            return enseignant.get();
        }

        Optional<Eleve> eleve = eleveRepository.findById(userId);
        if (eleve.isPresent()) {
            return eleve.get();
        }

        Optional<Parent> parent = parentRepository.findById(userId);
        if (parent.isPresent()) {
            return parent.get();
        }

        throw new KaladewnManagementException.EntityNotFoundException("User not found");
    }


    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token == null){
            log.error("Token is null");
            throw new KaladewnManagementException.TokenException("Token is null", null);
        }
        if(token.getExpiryDate().compareTo(Instant.now()) < 0 ){
            refreshTokenRepository.delete(token);
            throw new KaladewnManagementException.TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
        User user = refreshTokenRepository.findByToken(request.getRefreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new KaladewnManagementException.TokenException(request.getRefreshToken(),"Refresh token does not exist"));

        String token = jwtService.generateToken((UserDetails) user);
        return RefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(request.getRefreshToken())
                .tokenType(TokenType.BEARER.name())
                .build();
    }

    @Override
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return ResponseCookie.from(refreshTokenName, token)
                .path("/")
                .maxAge(refreshExpiration/1000) // 15 days in seconds
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return "";
        }
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }
    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenName, "")
                .path("/")
                .build();
    }
}
