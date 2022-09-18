package com.seven.Blog.API.controller;

import com.seven.Blog.API.DTO.UserDTO;
import com.seven.Blog.API.exception.BadRequestException;
import com.seven.Blog.API.payload.request.JwtAuthenticationRequest;
import com.seven.Blog.API.payload.response.JwtAuthenticationResponse;
import com.seven.Blog.API.security.JwtTokenHelper;
import com.seven.Blog.API.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(customUserDetailsService.registerUser(userDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthenticationRequest request) {

        try {
            authenticate(request.getEmail(), request.getPassword());
        } catch (DisabledException e) {
            throw new BadRequestException("User disabled !!!");
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Bad credentials !!!");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);
        return new ResponseEntity<>(jwtAuthenticationResponse, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

}
