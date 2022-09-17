package com.seven.Blog.API.service;

import com.seven.Blog.API.DTO.PostDTO;
import com.seven.Blog.API.entity.Post;
import com.seven.Blog.API.exception.ResourceNotFoundException;
import com.seven.Blog.API.repository.CommentRepository;
import com.seven.Blog.API.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PostDTO createPost(PostDTO postDTO) {
        Post post = postRepository.save(dto_to_class(postDTO));
        return class_to_dto(post);
    }

    public Set<PostDTO> findAllPost() {
        Set<Post> posts = new HashSet<>(postRepository.findAll());
        return posts.stream().map(this::class_to_dto).collect(Collectors.toSet());
    }

    public PostDTO updatePost(PostDTO postDTO, long postId) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        return class_to_dto(postRepository.save(dto_to_class(postDTO)));
    }

    public void deletePost(long postId) {
        if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post not found");
        postRepository.deleteById(postId);
    }

    // helper mapper
    public Post dto_to_class(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

    public PostDTO class_to_dto(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }
}
