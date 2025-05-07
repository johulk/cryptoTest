package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Comment;
import dev.m1guel.glacial.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findAllByPost(UUID postId, Pageable pageable);
    int countByPost(Post post);

}