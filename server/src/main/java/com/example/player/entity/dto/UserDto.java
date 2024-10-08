package com.example.player.entity.dto;

import com.example.player.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String confirmPassword;
    private String token;
    private String profile;


    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
        profile = user.getProfile();
    }
}
