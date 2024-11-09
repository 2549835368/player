package com.example.player.controller;

import cn.hutool.core.util.StrUtil;
import com.example.player.bean.Result;
import com.example.player.entity.User;
import com.example.player.entity.dto.UserDto;
import com.example.player.service.UserService;
import com.example.player.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.error(500,"输入不合法");
        }
        UserDto userDto = userService.login(user);

        return Result.success(userDto);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.error(500,"输入不合法");
        }
        User dbUser = userService.register(user);
        return Result.success(dbUser);
    }

    @GetMapping("/autologin")
    public Result autoLogin(@RequestParam("token") String token){
        return userService.autoLogin(token);
    }
}
