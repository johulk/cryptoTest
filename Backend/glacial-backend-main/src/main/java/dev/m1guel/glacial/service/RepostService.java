package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.Repost;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.PostRepository;
import dev.m1guel.glacial.repository.RepostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RepostService {

    private final RepostRepository repostRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public ResponseEntity<ResponseDto<String>> repost(UUID postId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        Post post = postOptional.get();
        if (repostRepository.findByUserAndPost(currentUser, post).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You have already reposted this post", null));
        }
        Repost repost = new Repost();
        repost.setUser(currentUser);
        repost.setPost(post);
        repostRepository.save(repost);
        return ResponseEntity.ok(new ResponseDto<>(true, "Post reposted successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> deleteRepost(UUID repostId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<Repost> repostOptional = repostRepository.findById(repostId);
        if (repostOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Repost not found", null));
        }
        Repost repost = repostOptional.get();
        if (!repost.getUser().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only delete your own reposts", null));
        }
        repostRepository.delete(repost);
        return ResponseEntity.ok(new ResponseDto<>(true, "Repost deleted successfully", null));
    }

    public ResponseEntity<ResponseDto<Integer>> getRepostCount(UUID postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Post not found", null));
        }
        int repostCount = repostRepository.countByPost(postOptional.get());
        return ResponseEntity.ok(new ResponseDto<>(true, "Repost count retrieved successfully", repostCount));
    }

}