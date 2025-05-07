package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findByUser(User user, Pageable pageable);
    List<Post> findByUser(User user);
}