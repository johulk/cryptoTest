package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Like;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {

    Optional<Like> findByUserAndPost(User user, Post post);
    List<Like> findAllByPost(Post post);
    int countByPost(Post post);

}