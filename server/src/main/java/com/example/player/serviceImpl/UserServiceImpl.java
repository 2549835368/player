package com.example.player.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.User;
import com.example.player.entity.dto.UserDto;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.UserMapper;
import com.example.player.service.UserService;
import com.example.player.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }
    @Override
    public UserDto login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser != null) {
            throw new ServiceException("用户已存在");
        }
        userMapper.insert(user);
        return user;
    }

    @Override
    public Result autoLogin(String token) {

        if(!JwtUtils.judgeTokenIsExist(token)){
            throw new ServiceException(500,"登录已过期");
        }
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User dbUser = userMapper.selectOne(queryWrapper);
        UserDto userDto = new UserDto(dbUser);
        userDto.setToken(JwtUtils.generateToken(userDto.getUsername()));
        return Result.success(userDto);
    }

}
