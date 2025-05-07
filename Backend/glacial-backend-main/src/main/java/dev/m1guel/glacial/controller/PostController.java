package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.FeedDto;
import dev.m1guel.glacial.dto.PostDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<ResponseDto<String>> createPost(@RequestBody @Valid PostDto postDto) {
        ResponseEntity<ResponseDto<String>> postCreatedResponse = postService.createPost(postDto);
        if (postCreatedResponse.getStatusCode().is2xxSuccessful()) {
            messagingTemplate.convertAndSend("/topic/feed", "post created");
            messagingTemplate.convertAndSend("/topic/feed", postDto);
        }
        return postCreatedResponse;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<FeedDto>>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getFeed(page, size);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<ResponseDto<List<FeedDto>>> getFeedByUser(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getFeedByUser(username, page, size);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<FeedDto>> getPostById(@PathVariable UUID postId) {
        return postService.getPostById(postId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> updatePost(
            @PathVariable UUID postId,
            @RequestBody @Valid PostDto postDto
    ) {
        ResponseEntity<ResponseDto<String>> updatedPostResponse = postService.updatePost(postId, postDto);
        if (updatedPostResponse.getStatusCode().is2xxSuccessful()) {
            messagingTemplate.convertAndSend("/topic/feed", "post updated");
            messagingTemplate.convertAndSend("/topic/feed", postDto);
        }
        return updatedPostResponse;
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> deletePost(@PathVariable UUID postId) {
        ResponseEntity<ResponseDto<String>> deletedPostResponse = postService.deletePost(postId);
        if (deletedPostResponse.getStatusCode().is2xxSuccessful()) {
            messagingTemplate.convertAndSend("/topic/feed", "post deleted");
            messagingTemplate.convertAndSend("/topic/feed", postId);
        }
        return deletedPostResponse;
    }

}