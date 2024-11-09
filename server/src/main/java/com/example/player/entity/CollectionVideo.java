package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_collection_video")
public class CollectionVideo {
    @TableId
    int cid;

    int vid;
    Timestamp time;
    int seq = 0;
}
