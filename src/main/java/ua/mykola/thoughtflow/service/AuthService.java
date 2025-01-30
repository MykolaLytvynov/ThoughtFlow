package ua.mykola.thoughtflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mykola.thoughtflow.api.dto.security.AuthResponse;
import ua.mykola.thoughtflow.api.dto.security.LoginRequest;
import ua.mykola.thoughtflow.api.dto.security.RefreshResponse;
import ua.mykola.thoughtflow.api.dto.security.RegisterRequest;
import ua.mykola.thoughtflow.document.User;
import ua.mykola.thoughtflow.exception.DuplicateException;
import ua.mykola.thoughtflow.exception.InvalidPasswordException;
import ua.mykola.thoughtflow.exception.NotFoundException;
import ua.mykola.thoughtflow.model.Topic;
import ua.mykola.thoughtflow.repository.UserRepository;
import ua.mykola.thoughtflow.security.JwtService;
import ua.mykola.thoughtflow.security.JwtValidator;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtValidator jwtValidator;

    public AuthResponse register(RegisterRequest request) {
        try {
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .favoriteTopics(request.getFavoriteTopics().stream()
                            .map(String::toUpperCase)
                            .map(Topic::valueOf)
                            .toList())
                    .build();
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateException(
                    String.format("Username '%s' is already taken.", request.getUsername())
            );
        }

        String accessToken = jwtService.generateAccessToken(request.getUsername());
        String refreshToken = jwtService.generateRefreshToken(request.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User was not found."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password.");
        }
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshResponse refreshAccessToken(String refreshToken) {
        jwtValidator.validateToken(refreshToken);

        String username = jwtService.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(username);

        return RefreshResponse.builder()
                .newAccessToken(newAccessToken)
                .build();
    }
}
