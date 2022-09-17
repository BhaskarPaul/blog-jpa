package com.seven.Blog.API.service;

import com.seven.Blog.API.DTO.CommentDTO;
import com.seven.Blog.API.entity.Comment;
import com.seven.Blog.API.entity.Post;
import com.seven.Blog.API.exception.GlobalException;
import com.seven.Blog.API.repository.CommentRepository;
import com.seven.Blog.API.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Set<CommentDTO> findAllByPost(long postId) {
        if (!postRepository.existsById(postId)) throw new GlobalException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new GlobalException("Post not found"));
        return commentRepository.findByPost(post).stream().map(this::class_to_dto).collect(Collectors.toSet());
    }

    public CommentDTO createComment(CommentDTO commentDTO, long postId) {
        if (!postRepository.existsById(postId)) throw new GlobalException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new GlobalException("Post not found"));
        Comment comment = dto_to_class(commentDTO);
        comment.setPost(post);
        return class_to_dto(commentRepository.save(comment));
    }

    public CommentDTO updateComment(CommentDTO commentDTO, long postId, long commentId) {
        if (!postRepository.existsById(postId)) throw new GlobalException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new GlobalException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new GlobalException("Comment not found"));
        comment.setText(commentDTO.getText());
        return class_to_dto(commentRepository.save(comment));
    }

    public void deleteComment(long postId, long commentId) {
        if (!postRepository.existsById(postId)) throw new GlobalException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new GlobalException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new GlobalException("Comment not found"));
        post.getCommentSet().remove(comment);
        commentRepository.delete(comment);
    }

    // helper mapper
    public Comment dto_to_class(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    public CommentDTO class_to_dto(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

}
