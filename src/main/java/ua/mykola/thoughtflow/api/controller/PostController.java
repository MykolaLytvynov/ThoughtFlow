package ua.mykola.thoughtflow.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.mykola.thoughtflow.api.dto.CommentRequest;
import ua.mykola.thoughtflow.api.dto.PagedResponse;
import ua.mykola.thoughtflow.api.dto.PostRequest;
import ua.mykola.thoughtflow.document.Post;
import ua.mykola.thoughtflow.document.User;
import ua.mykola.thoughtflow.service.PostService;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody PostRequest postRequest) {
        String username = user.getUsername();
        Post post = postService.save(username, postRequest);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{uuid}/comments")
    public ResponseEntity<Post> addComment(@AuthenticationPrincipal User user,
                                           @PathVariable String uuid,
                                           @Valid @RequestBody CommentRequest commentRequest) {
        String username = user.getUsername();
        Post updatedPost = postService.addComment(uuid, username, commentRequest);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Post> getPost(@PathVariable(name = "uuid") String uuid) {
        Post post = postService.findByUUID(uuid);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Post>> getPosts(@AuthenticationPrincipal User user,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "100") int size) {
        PagedResponse<Post> posts = postService.findByTopics(user.getFavoriteTopics(), size, page);
        return ResponseEntity.ok(posts);
    }
}
