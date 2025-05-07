package dev.m1guel.glacial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {

    @NotBlank(message = "Message is required")
    @Size(max = 255, message = "The message can't have more than 255 characters")
    private String message;

}