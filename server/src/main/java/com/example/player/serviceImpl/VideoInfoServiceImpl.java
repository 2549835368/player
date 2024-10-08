package com.example.player.serviceImpl;

import com.example.player.bean.Result;
import com.example.player.entity.Video;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoInfoServiceImpl implements VideoInfoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public Result getInfo(int vid) {
        Video dbVideo = videoMapper.selectById(vid);
        System.err.println(dbVideo.getTimestamp());
        return Result.success(dbVideo);
    }
}
