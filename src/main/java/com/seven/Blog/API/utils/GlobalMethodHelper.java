package com.seven.Blog.API.utils;

import com.seven.Blog.API.entity.User;
import com.seven.Blog.API.repository.UserRepository;
import com.seven.Blog.API.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GlobalMethodHelper {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUserDetails(String authToken) {
        authToken = authToken.substring(7);
        String email = jwtTokenHelper.getUsernameFromToken(authToken);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
