package com.example.player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.player.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_user")
    public List<User> find();

    @Select("select * from t_user where username = #{name}")
    public User findByUsername(String name);
}
