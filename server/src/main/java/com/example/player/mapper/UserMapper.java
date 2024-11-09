package com.example.player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.player.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT id FROM t_user where username = #{username}")
    User queryIdByUsername(String username);
}
