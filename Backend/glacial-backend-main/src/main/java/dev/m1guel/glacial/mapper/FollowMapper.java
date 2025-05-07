package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.model.Follow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FollowMapper {

    public HeaderDto toFollowerProfileDto(Follow follow) {
        return new HeaderDto(
                follow.getFollower().getUsername(),
                follow.getFollower().getAvatar(),
                follow.getFollower().getRole().getRoleName()
        );
    }

    public HeaderDto toFollowingProfileDto(Follow follow) {
        return new HeaderDto(
                follow.getFollowing().getUsername(),
                follow.getFollowing().getAvatar(),
                follow.getFollowing().getRole().getRoleName()
        );
    }

    public List<HeaderDto> toFollowerProfileDtoList(List<Follow> follows) {
        return follows.stream()
                .map(this::toFollowerProfileDto)
                .collect(Collectors.toList());
    }

    public List<HeaderDto> toFollowingProfileDtoList(List<Follow> follows) {
        return follows.stream()
                .map(this::toFollowingProfileDto)
                .collect(Collectors.toList());
    }

}