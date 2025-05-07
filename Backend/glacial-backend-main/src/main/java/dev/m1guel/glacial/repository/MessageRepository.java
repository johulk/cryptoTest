package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Message;
import dev.m1guel.glacial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findAllBySenderAndReceiver(User sender, User receiver);

}
