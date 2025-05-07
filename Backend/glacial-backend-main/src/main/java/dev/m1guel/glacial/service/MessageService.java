package dev.m1guel.glacial.service;

import dev.m1guel.glacial.dto.MessageDto;
import dev.m1guel.glacial.dto.ResponseDto;
import dev.m1guel.glacial.mapper.MessageMapper;
import dev.m1guel.glacial.model.Message;
import dev.m1guel.glacial.model.User;
import dev.m1guel.glacial.repository.MessageRepository;
import dev.m1guel.glacial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MessageMapper messageMapper;

    public ResponseEntity<ResponseDto<String>> sendMessage(UUID receiverId, MessageDto messageDto) {
        User sender = authService.getAuthenticatedUser();
        Optional<User> receiverOptional = userRepository.findById(receiverId);
        if (receiverOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Receiver not found", null));
        }
        User receiver = receiverOptional.get();
        if (sender.equals(receiver)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(false, "You cannot send a message to yourself", null));
        }
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageDto.getMessage());
        messageRepository.save(message);
        return ResponseEntity.ok(new ResponseDto<>(true, "Message sent successfully", null));
    }

    public ResponseEntity<ResponseDto<List<MessageDto>>> getConversation(UUID userId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<User> otherUserOptional = userRepository.findById(userId);
        if (otherUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "User not found", null));
        }
        User otherUser = otherUserOptional.get();
        List<Message> conversation = messageRepository.findAllBySenderAndReceiver(currentUser, otherUser);
        List<MessageDto> messages = messageMapper.toMessageDtoList(conversation);
        return ResponseEntity.ok(new ResponseDto<>(true, "Conversation retrieved successfully", messages));
    }

    public ResponseEntity<ResponseDto<String>> deleteMessage(UUID messageId) {
        User currentUser = authService.getAuthenticatedUser();
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto<>(false, "Message not found", null));
        }
        Message message = messageOptional.get();
        if (!message.getSender().equals(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseDto<>(false, "You can only delete your own messages", null));
        }
        messageRepository.delete(message);
        return ResponseEntity.ok(new ResponseDto<>(true, "Message deleted successfully", null));
    }

}