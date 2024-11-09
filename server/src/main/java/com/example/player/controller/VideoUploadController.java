package com.example.player.controller;

import cn.hutool.core.io.FileUtil;
import com.example.player.bean.Result;
import com.example.player.entity.Video;
import com.example.player.entity.dto.VideoChunkDto;
import com.example.player.service.VideoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/video")
public class VideoUploadController {

    @Value("${ip:localhost}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Autowired
    private VideoUploadService videoUploadService;

    @PostMapping("/preUpload")
    public Result preUpload(Video video){
        HashMap<String,Integer> res = videoUploadService.preUpload(video);
        return Result.success(res);
    }

    @PostMapping("/upload")
    public Result upload(VideoChunkDto videoChunkDto){
        String url = videoUploadService.chunkUpload(videoChunkDto);

        return Result.success(url);
    }

    @PostMapping("/getCurrentChunk")
    public Result getCurrentChunk(@RequestParam("md5") String md5){
        int index = videoUploadService.getCurrentChunk(md5);
        return Result.success(index);
    }

    @PostMapping("/mergeChunk")
    public Result mergeChunk(@RequestParam("md5") String md5,@RequestParam("id") int id){
        return videoUploadService.mergeChunk(md5,id);
    }

    @PostMapping("/deleteVideo")
    public Result deleteVideo(@RequestParam("id") int id){

        return Result.success(videoUploadService.deleteVideo(id));
    }

}

