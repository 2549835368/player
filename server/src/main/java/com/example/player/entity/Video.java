package com.example.player.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;


@Data
@TableName("t_video")
public class Video {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer authId;
    private Integer partId;
    private String tags;
    private String md5;
    private String url;
    private String coverUrl;
    private String suffix;
    @TableField(exist = false)
    private Integer max;
    private Integer collectionId;
    private Timestamp timestamp;
    private String profile;
    private Double duration;
    private Integer state;//0 待上传 1已上传 2通过审核
}
