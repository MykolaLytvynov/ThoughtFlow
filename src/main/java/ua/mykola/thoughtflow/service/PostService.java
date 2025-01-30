package ua.mykola.thoughtflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.mykola.thoughtflow.api.dto.CommentRequest;
import ua.mykola.thoughtflow.api.dto.PagedResponse;
import ua.mykola.thoughtflow.api.dto.PostRequest;
import ua.mykola.thoughtflow.model.Author;
import ua.mykola.thoughtflow.model.Comment;
import ua.mykola.thoughtflow.document.Post;
import ua.mykola.thoughtflow.model.Topic;
import ua.mykola.thoughtflow.exception.NotFoundException;
import ua.mykola.thoughtflow.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post save(String username, PostRequest postRequest) {
        Author author = Author.builder()
                .username(username)
                .build();

        Post post = Post.builder()
                .id(UUID.randomUUID().toString())
                .author(author)
                .topic(Topic.valueOf(postRequest.getTopic()))
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        return postRepository.save(post);
    }

    public Post addComment(String postUUID, String username, CommentRequest commentRequest) {
        Post post = findByUUID(postUUID);

        Comment newComment = Comment.builder()
                .username(username)
                .createdAt(LocalDateTime.now())
                .content(commentRequest.getComment()).build();

        Optional.ofNullable(post.getComments())
                .ifPresentOrElse(
                        comments -> {
                            comments.add(newComment);
                            comments.sort(Comparator.comparing(Comment::getCreatedAt, Comparator.reverseOrder()));
                        },
                        () -> post.setComments(List.of(newComment))
                );
        return postRepository.save(post);
    }

    public Post findByUUID(String uuid) {
        return postRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Post was not found with UUID: " + uuid));
    }

    public PagedResponse<Post> findByTopics(List<Topic> topics, int limit, int offset) {
        if (topics == null || topics.isEmpty()) {
            topics = Arrays.stream(Topic.values()).toList();
        }

        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findByTopicIn(topics, pageable);
        return new PagedResponse(postPage);
    }
}
