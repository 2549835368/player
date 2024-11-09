package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("t_comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    Integer id;
    int pid;
    String content;
    int uid;
    int vid;
    Timestamp createdTime;
    @TableField(exist = false)
    Page<Comment> childComment = new Page<>(0,0);

}
