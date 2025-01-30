package ua.mykola.thoughtflow.api.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    private String password;
}
