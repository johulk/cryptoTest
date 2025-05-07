package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.PostDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> createComment(
            @PathVariable UUID postId,
            @RequestBody @Valid PostDto postDto
    ) {
        return commentService.createComment(postId, postDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<List<PostDto>>> getCommentsByPost(
            @PathVariable UUID postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return commentService.getCommentsByPost(postId, page, size);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseDto<String>> updateComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid PostDto postDto
    ) {
        return commentService.updateComment(commentId, postDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<String>> deleteComment(@PathVariable UUID commentId) {
        return commentService.deleteComment(commentId);
    }

}