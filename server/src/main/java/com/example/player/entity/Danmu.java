package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("t_danmu")
public class Danmu {
    @TableId(type = IdType.AUTO)
    Integer id;
    int vid;
    String author;
    String color;
    double time;
    String text;
    int type;
}
