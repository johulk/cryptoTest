package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> likePost(@PathVariable UUID postId) {
        return likeService.likePost(postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> unlikePost(@PathVariable UUID postId) {
        return likeService.unlikePost(postId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<List<HeaderDto>>> getPostLikes(@PathVariable UUID postId) {
        return likeService.getPostLikes(postId);
    }

}