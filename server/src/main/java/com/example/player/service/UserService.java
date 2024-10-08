package com.example.player.service;

import com.example.player.entity.User;
import com.example.player.entity.dto.UserDto;

public interface UserService {
    UserDto login(User user);

    User register(User user);
}
