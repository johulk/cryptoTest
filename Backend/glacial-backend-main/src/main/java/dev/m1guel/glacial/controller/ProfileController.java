package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.*;
import dev.m1guel.glacial.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @DeleteMapping
    public ResponseEntity<ResponseDto<String>> deleteProfile() {
        return profileService.deleteProfile();
    }

    @PutMapping
    public ResponseEntity<ResponseDto<String>> updateProfile(@RequestBody @Valid ProfileDto profileDto) {
        return profileService.updateProfile(profileDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<HeaderDto>> getProfile() {
        return profileService.getProfile();
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDto<ProfileDto>> getUserProfile(@PathVariable String username) {
        return profileService.getUserProfile(username);
    }

    @PutMapping("/avatar")
    public ResponseEntity<ResponseDto<String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return profileService.uploadAvatar(file);
    }

    @PutMapping("/banner")
    public ResponseEntity<ResponseDto<String>> uploadBanner(@RequestParam("file") MultipartFile file) {
        return profileService.uploadBanner(file);
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDto<String>> updatePassword(@RequestBody @Valid PasswordDto passwordDto) {
        return profileService.updatePassword(passwordDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProfileDto>>> getAllUsers() {
        return profileService.getAllUsers();
    }

}