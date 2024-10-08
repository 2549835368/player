package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_collection")
public class Collection {
    Integer id;
    int cid;
    int vid;
    int seq;
}
