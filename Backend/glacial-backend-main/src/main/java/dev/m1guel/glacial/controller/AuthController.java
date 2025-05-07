package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.LoginDto;
import dev.m1guel.glacial.dto.RegisterDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<String>> login(@RequestBody @Valid LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> register(@RequestBody @Valid RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> logout() {
        return authService.logout();
    }

}