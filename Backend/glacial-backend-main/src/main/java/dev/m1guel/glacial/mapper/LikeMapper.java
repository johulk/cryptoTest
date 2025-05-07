package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.model.Like;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeMapper {

    public HeaderDto toProfileDto(Like like) {
        return new HeaderDto(
                like.getUser().getUsername(),
                like.getUser().getAvatar(),
                like.getUser().getRole().getRoleName()
        );
    }

    public List<HeaderDto> toProfileDtoList(List<Like> likes) {
        return likes.stream()
                .map(this::toProfileDto)
                .collect(Collectors.toList());
    }

}