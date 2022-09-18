package com.seven.Blog.API.repository;

import com.seven.Blog.API.entity.Post;
import com.seven.Blog.API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    boolean existsById(Long aLong);

    List<Post> findByUser(User user);


}