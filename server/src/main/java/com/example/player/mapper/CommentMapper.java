package com.example.player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.player.entity.Comment;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
