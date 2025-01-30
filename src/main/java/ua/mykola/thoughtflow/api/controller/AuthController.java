package ua.mykola.thoughtflow.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ua.mykola.thoughtflow.api.dto.security.AuthResponse;
import ua.mykola.thoughtflow.api.dto.security.LoginRequest;
import ua.mykola.thoughtflow.api.dto.security.RefreshRequest;
import ua.mykola.thoughtflow.api.dto.security.RefreshResponse;
import ua.mykola.thoughtflow.api.dto.security.RegisterRequest;
import ua.mykola.thoughtflow.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        RefreshResponse refreshResponse = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(refreshResponse);
    }
}
