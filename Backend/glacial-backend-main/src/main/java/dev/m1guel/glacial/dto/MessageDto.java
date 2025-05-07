package dev.m1guel.glacial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private String sender;
    private String receiver;
    private String message;
    private LocalDateTime createdAt;

}
