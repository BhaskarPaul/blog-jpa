package com.seven.Blog.API.service;

import com.seven.Blog.API.DTO.CommentDTO;
import com.seven.Blog.API.entity.Comment;
import com.seven.Blog.API.entity.Post;
import com.seven.Blog.API.entity.User;
import com.seven.Blog.API.exception.ResourceNotFoundException;
import com.seven.Blog.API.repository.CommentRepository;
import com.seven.Blog.API.repository.PostRepository;
import com.seven.Blog.API.utils.GlobalMethodHelper;
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
    private GlobalMethodHelper helper;

    @Autowired
    private ModelMapper modelMapper;

    public Set<CommentDTO> findAllByPost(long postId) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return commentRepository.findByPost(post).stream().map(this::class_to_dto).collect(Collectors.toSet());
    }

    public Set<CommentDTO> findAllByUser(String token) {
        User user = helper.getCurrentUserDetails(token);
        return commentRepository.findByUser(user).stream().map(this::class_to_dto).collect(Collectors.toSet());
    }

    public CommentDTO createComment(CommentDTO commentDTO, long postId, String token) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = helper.getCurrentUserDetails(token);
        Comment comment = dto_to_class(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        return class_to_dto(commentRepository.save(comment));
    }

    public CommentDTO updateComment(CommentDTO commentDTO, long postId, long commentId) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        comment.setText(commentDTO.getText());
        return class_to_dto(commentRepository.save(comment));
    }

    public void deleteComment(long postId, long commentId) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
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
