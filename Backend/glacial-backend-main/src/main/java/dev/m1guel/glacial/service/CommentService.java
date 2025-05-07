package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.PostDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.CommentMapper;
import dev.m1guel.glacial.model.Comment;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.CommentRepository;
import dev.m1guel.glacial.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public ResponseEntity<ResponseDto<String>> createComment(UUID postId, PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        User currentUser = authService.getAuthenticatedUser();
        Comment comment = commentMapper.toComment(postDto, postOptional.get(), currentUser);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(true, "Comment created successfully", null)
        );
    }

    public ResponseEntity<ResponseDto<List<PostDto>>> getCommentsByPost(UUID postId, int page, int size) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Comment> commentsPage = commentRepository.findAllByPost(postId, pageable);
        if (commentsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ResponseDto<>(false, "No comments available", null)
            );
        }
        List<PostDto> postComments = commentsPage.getContent().stream()
                .map(commentMapper::toPostDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(true, "Post comments fetched successfully", postComments)
        );
    }

    public ResponseEntity<ResponseDto<String>> updateComment(UUID commentId, PostDto postDto) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Comment not found", null));
        }
        Comment comment = commentOpt.get();
        User currentUser = authService.getAuthenticatedUser();
        if (!comment.getUser().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only edit your own comments", null));
        }
        comment.setMessage(postDto.getMessage());
        commentRepository.save(comment);
        return ResponseEntity.ok(new ResponseDto<>(true, "Comment updated successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> deleteComment(UUID commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Comment not found", null));
        }
        Comment comment = commentOpt.get();
        User currentUser = authService.getAuthenticatedUser();
        if (!comment.getUser().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only delete your own comments", null));
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok(new ResponseDto<>(true, "Comment deleted successfully", null));
    }

}