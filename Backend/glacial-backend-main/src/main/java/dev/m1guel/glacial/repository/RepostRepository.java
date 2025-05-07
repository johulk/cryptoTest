package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.Repost;
import dev.m1guel.glacial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepostRepository extends JpaRepository<Repost, UUID> {

    Optional<Repost> findByUserAndPost(User user, Post post);
    int countByPost(Post post);

}