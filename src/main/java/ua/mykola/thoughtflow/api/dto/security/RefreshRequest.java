package ua.mykola.thoughtflow.api.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    @NotBlank(message = "Refresh token is required.")
    private String refreshToken;
}
