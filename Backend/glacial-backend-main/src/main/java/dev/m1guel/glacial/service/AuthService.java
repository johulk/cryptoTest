package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.LoginDto;
import dev.m1guel.glacial.dto.RegisterDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.AuthMapper;
import dev.m1guel.glacial.model.Role;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;
    private final JwtService jwtService;

    public ResponseEntity<ResponseDto<String>> register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto<>(false, "Username already in use", null));
        }
        User newUser = authMapper.toUser(registerDto);
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto<>(true, "User successfully registered", null));
    }

    public ResponseEntity<ResponseDto<String>> login(LoginDto loginDto) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null && currentAuth.isAuthenticated() && !(currentAuth.getPrincipal() instanceof String)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto<>(false, "User is already logged in", null)
            );
        }
        if (!userRepository.existsByUsername(loginDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDto<>(false, "Username not found", null)
            );
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(true, "User successfully logged in", jwtToken)
        );
    }

    public ResponseEntity<ResponseDto<String>> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDto<>(true, "User successfully logged out", null)
        );
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

}