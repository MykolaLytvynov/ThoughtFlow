package ua.mykola.thoughtflow.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Comment {
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
