package com.seven.Blog.API.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private long id;
    private String title;
    private String description;
    private long likes;
    private long views;
}
