package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.RepostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/repost")
public class RepostController {

    private final RepostService repostService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseDto<String>> repost(@PathVariable UUID postId) {
        return repostService.repost(postId);
    }

    @DeleteMapping("/{repostId}")
    public ResponseEntity<ResponseDto<String>> deleteRepost(@PathVariable UUID repostId) {
        return repostService.deleteRepost(repostId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<Integer>> getRepostCount(@PathVariable UUID postId) {
        return repostService.getRepostCount(postId);
    }

}
