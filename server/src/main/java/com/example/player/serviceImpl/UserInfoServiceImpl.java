package com.example.player.serviceImpl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.player.bean.Result;
import com.example.player.entity.User;
import com.example.player.entity.dto.UserDto;
import com.example.player.mapper.UserMapper;
import com.example.player.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserMapper userMapper;
    public Result getById(int id){
        User dbUser =userMapper.selectById(id);
        UserDto userDto = new UserDto(dbUser);
        return Result.success(userDto);
    }

    @Override
    public Result getNamesById(ArrayList<Integer> idList) {
        JSONObject jsonObject = new JSONObject();
//        HashMap<String,String> hashMap = new HashMap<>();
        for (int i:idList
             ) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id","nickname").eq("id",i);
            User dbUser = userMapper.selectOne(queryWrapper);
//            hashMap.put(String.valueOf(dbUser.getId()),dbUser.getUsername());
            jsonObject.set(dbUser.getId().toString(),dbUser.getNickname());
        }
        System.err.println(jsonObject);
        return Result.success(jsonObject);
    }


}
