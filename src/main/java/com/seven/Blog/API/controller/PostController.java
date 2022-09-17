package com.seven.Blog.API.controller;

import com.seven.Blog.API.DTO.PostDTO;
import com.seven.Blog.API.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    // create
    @PostMapping()
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    // read
    @GetMapping()
    public ResponseEntity<?> findAllPost() {
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }

    // update
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO postDTO, @PathVariable long postId) {
        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }
}