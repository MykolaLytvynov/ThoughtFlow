package ua.mykola.thoughtflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ua.mykola.thoughtflow.api.validation.ValidEnum;
import ua.mykola.thoughtflow.model.Topic;

@Getter
@Setter
public class PostRequest {
    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters.")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 300, message = "Content must be between 10 and 300 characters.")
    private String content;

    @NotNull(message = "Topic must be added to Post.")
    @ValidEnum(enumClass = Topic.class, message = "Invalid topic. Allowed values: TECHNOLOGY, SCIENCE, EDUCATION, ART, SPORTS, HEALTH.")
    private String topic;
}
