package com.example.player.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.entity.User;
import com.example.player.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private UserMapper usermapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @GetMapping("/index")
    public String index(){
        List<User> list = usermapper.selectList(null);
        System.out.println(list);
        return list.toString();
    }

    @GetMapping("/redis")
    public String redis(){
        redisTemplate.opsForValue().set("name","liu");
        return (String) redisTemplate.opsForValue().get("name");
    }

    @GetMapping("/query")
    public User selectByName(@RequestParam String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = usermapper.selectOne(queryWrapper);
        System.out.println(user);
        return user;
    }
}
