package com.seven.Blog.API.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RootExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> globalExceptionHandler(GlobalException globalException) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", globalException.getMessage());
        response.put("status", HttpStatus.BAD_GATEWAY.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
