package com.seven.Blog.API.repository;

import com.seven.Blog.API.entity.Comment;
import com.seven.Blog.API.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    boolean existsById(Long aLong);

    List<Comment> findByPost(Post post);


}