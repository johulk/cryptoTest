package dev.m1guel.glacial.mapper;

import dev.m1guel.glacial.dto.PostDto;
import dev.m1guel.glacial.model.Comment;
import dev.m1guel.glacial.model.Post;
import dev.m1guel.glacial.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toComment(PostDto postDto, Post post, User user) {
        Comment comment = new Comment();
        comment.setMessage(postDto.getMessage());
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }

    public PostDto toPostDto(Comment comment) {
        return new PostDto(comment.getMessage());
    }

}