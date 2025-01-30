package ua.mykola.thoughtflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    @NotBlank
    @Size(min = 2, max = 250, message = "Comment must be between 2 and 250 characters.")
    private String comment;
}
