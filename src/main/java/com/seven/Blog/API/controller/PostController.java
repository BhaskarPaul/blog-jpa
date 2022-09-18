package com.seven.Blog.API.controller;

import com.seven.Blog.API.DTO.PostDTO;
import com.seven.Blog.API.service.PostService;
import com.seven.Blog.API.utils.GlobalMethodHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private GlobalMethodHelper helper;

    // create
    @PostMapping()
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String token, @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO, token), HttpStatus.CREATED);
    }

    // read
    @GetMapping()
    public ResponseEntity<?> findAllPost() {
        return new ResponseEntity<>(postService.findAllPost(), HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<?> findAllByUser(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(postService.findAllByUser(token), HttpStatus.OK);
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