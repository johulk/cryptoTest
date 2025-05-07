package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.FeedDto;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.repository.CommentRepository;
import dev.m1guel.glacial.repository.LikeRepository;
import dev.m1guel.glacial.repository.RepostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PostMapper {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final RepostRepository repostRepository;

    public FeedDto toFeedDto(Post post) {
        return new FeedDto(
                post.getId(),
                post.getMessage(),
                post.getUser().getUsername(),
                post.getCreatedAt(),
                likeRepository.countByPost(post),
                commentRepository.countByPost(post),
                repostRepository.countByPost(post)
        );
    }

    public List<FeedDto> toFeedDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::toFeedDto)
                .collect(Collectors.toList());
    }

}