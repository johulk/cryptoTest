package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.HeaderDto;
import dev.m1guel.glacial.dto.PasswordDto;
import dev.m1guel.glacial.dto.ProfileDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.ProfileMapper;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.FollowRepository;
import dev.m1guel.glacial.repository.PostRepository;
import dev.m1guel.glacial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final ProfileMapper profileMapper;

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    private final String AVATAR_DIR = UPLOAD_DIR + "avatars/";
    private final String BANNER_DIR = UPLOAD_DIR + "banners/";

    public ResponseEntity<ResponseDto<HeaderDto>> getProfile() {
        User currentUser = authService.getAuthenticatedUser();
        HeaderDto profileDto = profileMapper.toHeaderDto(currentUser);
        return ResponseEntity.ok(new ResponseDto<>(true, "Profile retrieved successfully", profileDto));
    }

    public ResponseEntity<ResponseDto<ProfileDto>> getUserProfile(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        int followersCount = followRepository.findAllByFollower(userOptional.get()).size();
        int followingCount = followRepository.findAllByFollowing(userOptional.get()).size();
        int postsCount = postRepository.findByUser(userOptional.get()).size();
        ProfileDto profileDto = profileMapper.toProfileDto(userOptional.get(), followersCount, followingCount, postsCount);
        return ResponseEntity.ok(new ResponseDto<>(true, "Profile retrieved successfully", profileDto));
    }

    public ResponseEntity<ResponseDto<String>> updateProfile(ProfileDto profileDto) {
        User currentUser = authService.getAuthenticatedUser();
        if (!currentUser.getUsername().equals(profileDto.getUsername()) &&
                userRepository.findByUsername(profileDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDto<>(false, "Username is already taken", null));
        }
        profileMapper.updateUserFromDto(currentUser, profileDto);
        userRepository.save(currentUser);
        return ResponseEntity.ok(new ResponseDto<>(true, "Profile updated successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> updatePassword(PasswordDto passwordDto) {
        User currentUser = authService.getAuthenticatedUser();
        if (!passwordEncoder.matches(passwordDto.getCurrentPassword(), currentUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "Incorrect current password", null));
        }
        if (passwordDto.getCurrentPassword().equals(passwordDto.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "New password must be different from the current password", null));
        }
        currentUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(currentUser);
        return ResponseEntity.ok(new ResponseDto<>(true, "Password updated successfully", null));
    }

    public ResponseEntity<ResponseDto<String>> uploadAvatar(MultipartFile file) {
        return uploadImage(file, AVATAR_DIR, "Avatar uploaded successfully");
    }

    public ResponseEntity<ResponseDto<String>> uploadBanner(MultipartFile file) {
        return uploadImage(file, BANNER_DIR, "Banner uploaded successfully");
    }

    public ResponseEntity<ResponseDto<String>> deleteProfile() {
        User currentUser = authService.getAuthenticatedUser();
        userRepository.deleteById(currentUser.getId());
        return ResponseEntity.ok(new ResponseDto<>(true, "Profile deleted successfully", null));
    }

    private ResponseEntity<ResponseDto<String>> uploadImage(MultipartFile file, String directory, String successMessage) {
        User currentUser = authService.getAuthenticatedUser();
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto<>(false, "File is empty", null));
        }
        String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!isValidImageExtension(fileExtension)) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDto<>(false, "Invalid file type", null));
        }
        String filename = UUID.randomUUID() + fileExtension;
        Path fileStorageLocation = Paths.get(directory).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, REPLACE_EXISTING);
            if (AVATAR_DIR.equals(directory)) {
                currentUser.setAvatar(filename);
            } else if (BANNER_DIR.equals(directory)) {
                currentUser.setBanner(filename);
            }
            userRepository.save(currentUser);
            return ResponseEntity.ok(new ResponseDto<>(true, successMessage, filename));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto<>(false, "Unable to upload image: " + e.getMessage(), null));
        }
    }

    private String getFileExtension(String filename) {
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".")).toLowerCase();
        }
        return ".png";
    }

    private boolean isValidImageExtension(String extension) {
        return List.of(".png", ".jpg", ".jpeg").contains(extension);
    }

    //Igor
    public ResponseEntity<ResponseDto<List<ProfileDto>>> getAllUsers() {
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDto<>(false, "No users found", null));
    }
    
    List<ProfileDto> profileDtos = users.stream()
            .map(user -> profileMapper.toProfileDto(user, 
                    followRepository.findAllByFollower(user).size(),
                    followRepository.findAllByFollowing(user).size(),
                    postRepository.findByUser(user).size()))
            .collect(Collectors.toList());

    return ResponseEntity.ok(new ResponseDto<>(true, "Users retrieved successfully", profileDtos));
}
}