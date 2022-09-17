package com.seven.Blog.API.repository;

import com.seven.Blog.API.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    boolean existsById(Long aLong);
}