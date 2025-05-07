package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.model.Role;
import dev.m1guel.glacial.model.User;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public HeaderDto toHeaderDto(User user) {
        return new HeaderDto(
                user.getUsername(),
                user.getAvatar(),
                user.getRole().getRoleName()
        );
    }

    public ProfileDto toProfileDto(User user, int followers, int following, int posts) {
        return new ProfileDto(
                user.getUsername(),
                user.getWalletSol(),
                user.getWalletEth(),
                user.getAvatar(),
                user.getBanner(),
                user.getBio(),
                user.getRole().getRoleName(),
                followers,
                following,
                posts
        );
    }

    public void updateUserFromDto(User user, ProfileDto profileDto) {
        user.setUsername(profileDto.getUsername());
        user.setWalletSol(profileDto.getWalletSol());
        user.setWalletEth(profileDto.getWalletEth());
        user.setRole(Role.fromString(profileDto.getRole()));
        user.setBio(profileDto.getBio());
    }

}