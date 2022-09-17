package com.seven.Blog.API.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String email;
    private String password;
}
