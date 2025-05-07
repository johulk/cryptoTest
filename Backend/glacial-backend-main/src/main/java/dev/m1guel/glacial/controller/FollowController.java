package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseDto<String>> followUser(@PathVariable UUID userId) {
        return followService.followUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto<String>> unfollowUser(@PathVariable UUID userId) {
        return followService.unfollowUser(userId);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<ResponseDto<List<HeaderDto>>> getFollowers(@PathVariable UUID userId) {
        return followService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<ResponseDto<List<HeaderDto>>> getFollowing(@PathVariable UUID userId) {
        return followService.getFollowing(userId);
    }

}