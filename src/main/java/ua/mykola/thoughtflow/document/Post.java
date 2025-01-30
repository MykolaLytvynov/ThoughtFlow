package ua.mykola.thoughtflow.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ua.mykola.thoughtflow.model.Author;
import ua.mykola.thoughtflow.model.Comment;
import ua.mykola.thoughtflow.model.Topic;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private Author author;
    private Topic topic;
    private List<Comment> comments;
    private LocalDateTime createdAt;
}


