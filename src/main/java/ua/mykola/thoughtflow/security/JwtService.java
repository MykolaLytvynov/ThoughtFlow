package ua.mykola.thoughtflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mykola.thoughtflow.config.JwtProperties;
import ua.mykola.thoughtflow.exception.InvalidTokenException;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    public String generateAccessToken(String username) {
        return generateToken(username, jwtProperties.getAccessExpiration());
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtProperties.getRefreshExpiration());
    }

    public String getUsernameFromToken(String token) {
        return Optional.ofNullable(parseToken(token))
                .map(claims -> claims.getSubject())
                .filter(subject -> subject != null)
                .orElseThrow(() -> new InvalidTokenException("Token does not contain a valid username."));
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
