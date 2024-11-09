package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.Video;

import java.util.HashMap;

public interface VideoInfoService {
    public Result getInfo(int vid);

    Result getListByUid(int uid);

    Result submit(Video video);

    Result getVideoList(int num,int part);
}
