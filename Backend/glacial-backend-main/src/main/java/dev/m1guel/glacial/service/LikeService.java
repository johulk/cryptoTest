package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.LikeMapper;
import dev.m1guel.glacial.model.Like;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.LikeRepository;
import dev.m1guel.glacial.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final LikeMapper likeMapper;

    public ResponseEntity<ResponseDto<String>> likePost(UUID postId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        Post post = postOptional.get();
        Optional<Like> existingLike = likeRepository.findByUserAndPost(currentUser, post);
        if (existingLike.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You have already liked this post", null));
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        likeRepository.save(like);
        return ResponseEntity.ok(new ResponseDto<>(true, "Post liked successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> unlikePost(UUID postId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        Post post = postOptional.get();
        Optional<Like> existingLike = likeRepository.findByUserAndPost(currentUser, post);
        if (existingLike.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You haven't liked this post yet", null));
        }
        likeRepository.delete(existingLike.get());
        return ResponseEntity.ok(new ResponseDto<>(true, "Post unliked successfully", null));
    }

    public ResponseEntity<ResponseDto<List<HeaderDto>>> getPostLikes(UUID postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        List<Like> likes = likeRepository.findAllByPost(postOptional.get());
        if (likes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDto<>(false, "No likes found", null));
        }
        List<HeaderDto> likedUsers = likeMapper.toProfileDtoList(likes);
        return ResponseEntity.ok(new ResponseDto<>(true, "Likes retrieved successfully", likedUsers));
    }

}