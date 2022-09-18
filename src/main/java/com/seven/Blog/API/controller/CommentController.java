package com.seven.Blog.API.controller;

import com.seven.Blog.API.DTO.CommentDTO;
import com.seven.Blog.API.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/comment/")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("{postId}")
    public ResponseEntity<?> findAllByPost(@PathVariable long postId) {
        return new ResponseEntity<>(commentService.findAllByPost(postId), HttpStatus.OK);
    }

    @GetMapping("user")
    public ResponseEntity<?> findAllByUser(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(commentService.findAllByUser(token), HttpStatus.OK);
    }

    @PostMapping("{postId}")
    public ResponseEntity<?> createComment(@RequestHeader("Authorization") String token, @RequestBody CommentDTO commentDTO, @PathVariable long postId) {
        return new ResponseEntity<>(commentService.createComment(commentDTO, postId, token), HttpStatus.OK);
    }

    @PutMapping("{postId}/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable long postId, @PathVariable long commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentDTO, postId, commentId), HttpStatus.OK);
    }

    @DeleteMapping("{postId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
