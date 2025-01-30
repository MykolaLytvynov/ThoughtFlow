package ua.mykola.thoughtflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.mykola.thoughtflow.exception.InvalidTokenException;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtValidator {
    private final JwtService jwtService;

    public void validateToken(String token) {
        try {
            Claims claims = jwtService.parseToken(token);

            if (claims.getExpiration().before(new Date())) {
                throw new InvalidTokenException("Token has expired.");
            }

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token: " + e.getMessage());
        }
    }
}
