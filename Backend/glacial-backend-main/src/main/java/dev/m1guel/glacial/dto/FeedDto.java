package dev.m1guel.glacial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class FeedDto {

    private UUID id;
    private String message;
    private String authorName;
    private LocalDateTime createdAt;
    private int likesCount;
    private int commentsCount;
    private int repostsCount;

}
