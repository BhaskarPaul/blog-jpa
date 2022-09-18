package com.seven.Blog.API.service;

import com.seven.Blog.API.DTO.UserDTO;
import com.seven.Blog.API.entity.User;
import com.seven.Blog.API.exception.ResourceNotFoundException;
import com.seven.Blog.API.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = dto_to_class(userDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        return class_to_dto(userRepository.save(user));
    }



    // helper methods
    public User dto_to_class(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO class_to_dto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
