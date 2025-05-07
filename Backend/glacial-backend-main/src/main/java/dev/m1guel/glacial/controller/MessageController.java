package dev.m1guel.glacial.controller;

import dev.m1guel.glacial.dto.MessageDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{receiverId}")
    public ResponseEntity<ResponseDto<String>> sendMessage(
            @PathVariable UUID receiverId,
            @RequestBody @Valid MessageDto messageDto
    ) {
        return messageService.sendMessage(receiverId, messageDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto<List<MessageDto>>> getConversation(@PathVariable UUID userId) {
        return messageService.getConversation(userId);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ResponseDto<String>> deleteMessage(@PathVariable UUID messageId) {
        return messageService.deleteMessage(messageId);
    }

}