package com.example.player.serviceImpl;

import com.example.player.entity.User;
import com.example.player.entity.dto.UserDto;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.UserMapper;
import com.example.player.service.UserService;
import com.example.player.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public UserDto login(User user) {
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser == null) {
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        UserDto userDto = new UserDto(dbUser);
        userDto.setToken(JwtUtils.generateToken(userDto.getUsername()));
        return userDto;
    }

    @Override
    public User register(User user) {
        User dbUser = userMapper.findByUsername(user.getUsername());
        if (dbUser != null) {
            throw new ServiceException("用户已存在");
        }
        userMapper.insert(user);
        return user;
    }

}
