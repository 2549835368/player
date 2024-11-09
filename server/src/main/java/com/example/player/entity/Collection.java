package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_collection")
public class Collection{
    @TableId(type = IdType.AUTO)
    Integer id;
    int uid;
    String title;
    Timestamp createdTime;
}
