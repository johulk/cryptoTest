package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.FollowMapper;
import dev.m1guel.glacial.model.Follow;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.FollowRepository;
import dev.m1guel.glacial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final AuthService authService;
    private final FollowMapper followMapper;

    public ResponseEntity<ResponseDto<String>> followUser(UUID userId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<User> targetUserOptional = userRepository.findById(userId);
        if (targetUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        User targetUser = targetUserOptional.get();
        if (currentUser.equals(targetUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You cannot follow yourself", null));
        }
        Optional<Follow> followOptional = followRepository.findByFollowerAndFollowing(currentUser, targetUser);
        if (followOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You are already following this user", null));
        }
        Follow follow = new Follow();
        follow.setFollower(currentUser);
        follow.setFollowing(targetUser);
        followRepository.save(follow);
        return ResponseEntity.ok(new ResponseDto<>(true, "User followed successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> unfollowUser(UUID userId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<User> targetUserOptional = userRepository.findById(userId);
        if (targetUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        User targetUser = targetUserOptional.get();
        Optional<Follow> followOptional = followRepository.findByFollowerAndFollowing(currentUser, targetUser);
        if (followOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You are not following this user", null));
        }
        followRepository.delete(followOptional.get());
        return ResponseEntity.ok(new ResponseDto<>(true, "User unfollowed successfully", null));
    }

    public ResponseEntity<ResponseDto<List<HeaderDto>>> getFollowers(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        List<Follow> followers = followRepository.findAllByFollowing(userOptional.get());
        if (followers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDto<>(false, "No followers found", null));
        }
        List<HeaderDto> users = followMapper.toFollowerProfileDtoList(followers);
        return ResponseEntity.ok(new ResponseDto<>(true, "Followers retrieved successfully", users));
    }

    public ResponseEntity<ResponseDto<List<HeaderDto>>> getFollowing(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        List<Follow> following = followRepository.findAllByFollower(userOptional.get());
        if (following.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDto<>(false, "No following found", null));
        }
        List<HeaderDto> users = followMapper.toFollowingProfileDtoList(following);
        return ResponseEntity.ok(new ResponseDto<>(true, "Following retrieved successfully", users));
    }

}