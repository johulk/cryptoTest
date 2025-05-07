package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.MessageDto;
import dev.m1guel.glacial.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {

    public MessageDto toMessageDto(Message message) {
        return new MessageDto(
                message.getSender().getUsername(),
                message.getReceiver().getUsername(),
                message.getMessage(),
                message.getCreatedAt()
        );
    }

    public List<MessageDto> toMessageDtoList(List<Message> messages) {
        return messages.stream()
                .map(this::toMessageDto)
                .collect(Collectors.toList());
    }

}