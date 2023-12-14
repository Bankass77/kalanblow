package ml.kalanblow.gestiondesinscriptions.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import ml.kalanblow.gestiondesinscriptions.enums.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    @Value("${application.security.jwt.expiration}")
    private  long JWT_TOKEN_VALIDITY_SECONDS ;

    @Value("${application.security.jwt.secret-key}")
    private  String JWT_TOKEN_SECRET ;

    @Value("${application.security.jwt.token.issuer}")
    private  String JWT_TOKEN_ISSUER ;

    @Value("${application.security.jwt.token.audience}")
    private  String JWT_TOKEN_AUDIENCE ;

    @Value("${application.security.jwt.cookie-name}")
    private String jwtCookieName;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_TOKEN_SECRET).parseClaimsJws(stripTokenPrefix(token)).getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return TokenType.BEARER.name() + " " + doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_SECONDS * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .setIssuer(JWT_TOKEN_ISSUER)
                .setAudience(JWT_TOKEN_AUDIENCE)
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET)
                .compact();
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(stripTokenPrefix(token), Claims::getExpiration);
    }

    private String stripTokenPrefix(String token) {
        if (token != null && token.startsWith(TokenType.BEARER.name())) {
            return token.substring(TokenType.BEARER.name().length()).trim();
        }
        return token;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    
    public ResponseCookie generateJwtCookie(String jwt) {
        return ResponseCookie.from(jwtCookieName, jwt)
                .path("/")
                .maxAge(24 * 60 * 60) // 24 hours
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "")
                .path("/")
                .build();
    }
}
