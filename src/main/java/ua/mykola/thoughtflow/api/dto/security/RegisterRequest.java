package ua.mykola.thoughtflow.api.dto.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ua.mykola.thoughtflow.api.validation.ValidEnum;
import ua.mykola.thoughtflow.model.Topic;

import java.util.List;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 4, max = 15, message = "Username must be between 4 and 15 characters.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @Size(min = 4, message = "Password must contain at least 4 characters.")
    private String password;

    @ValidEnum(enumClass = Topic.class, message = "Invalid topic in array. Allowed values: TECHNOLOGY, SCIENCE, EDUCATION, ART, SPORTS, HEALTH.")
    private List<String> favoriteTopics;
}
