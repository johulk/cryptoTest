package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.RegisterDto;
import dev.m1guel.glacial.model.Role;
import dev.m1guel.glacial.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setWalletSol(isEmpty(registerDto.getWalletSol()) ? null : registerDto.getWalletSol());
        user.setWalletEth(isEmpty(registerDto.getWalletEth()) ? null : registerDto.getWalletEth());
        user.setRole(Role.USER);
        user.setBio(null);
        user.setAvatar(null);
        user.setBanner(null);

        return user;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}