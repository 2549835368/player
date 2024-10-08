package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_part_caption")
@Data
public class PartCaption {
    @TableId(type = IdType.AUTO)
    Integer id;
    int code;
    String caption;
}
