package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.FeedDto;
import dev.m1guel.glacial.dto.PostDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.PostMapper;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public ResponseEntity<ResponseDto<String>> createPost(PostDto postDto) {
        User currentUser = authService.getAuthenticatedUser();
        Post post = new Post();
        post.setUser(currentUser);
        post.setMessage(postDto.getMessage());
        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(true, "Post created successfully", null)
        );
    }

    public ResponseEntity<ResponseDto<List<FeedDto>>> getFeed(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> postsPage = postRepository.findAll(pageable);
        if (postsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDto<>(false, "No posts available", null));
        }
        List<FeedDto> feed = postMapper.toFeedDtoList(postsPage.getContent());
        return ResponseEntity.ok(new ResponseDto<>(true, "Feed fetched successfully", feed));
    }

    public ResponseEntity<ResponseDto<List<FeedDto>>> getFeedByUser(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        User user = userOptional.get();
        Page<Post> userPostsPage = postRepository.findByUser(user, pageable);
        if (userPostsPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDto<>(false, "No posts available", null));
        }
        List<FeedDto> userPosts = postMapper.toFeedDtoList(userPostsPage.getContent());
        return ResponseEntity.ok(new ResponseDto<>(true, "User posts fetched successfully", userPosts));
    }

    public ResponseEntity<ResponseDto<FeedDto>> getPostById(UUID id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        FeedDto feedDto = postMapper.toFeedDto(postOptional.get());
        return ResponseEntity.ok(new ResponseDto<>(true, "Post fetched successfully", feedDto));
    }

    public ResponseEntity<ResponseDto<String>> updatePost(UUID id, PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDto<>(false, "Post not found", null)
            );
        }
        Post post = postOptional.get();
        User currentUser = authService.getAuthenticatedUser();
        if (!post.getUser().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only update your own posts", null));
        }
        LocalDateTime createdAt = post.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);
        if (duration.toMinutes() > 5) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only edit posts within 5 minutes of creation", null));
        }
        post.setMessage(postDto.getMessage());
        postRepository.save(post);
        return ResponseEntity.ok(new ResponseDto<>(true, "Post updated successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> deletePost(UUID id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDto<>(false, "Post not found", null)
            );
        }
        Post post = postOptional.get();
        User currentUser = authService.getAuthenticatedUser();
        if (!post.getUser().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseDto<>(false, "You can only delete your own posts", null)
            );
        }
        postRepository.delete(post);
        return ResponseEntity.ok(new ResponseDto<>(true, "Post deleted successfully", null));
    }

}