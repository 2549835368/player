package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.Video;
import com.example.player.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/videoInfo")
public class VideoInfoController {
    @Autowired
    VideoInfoService videoInfoService;

    @GetMapping("/getInfo")
    public Result getInfo(@RequestParam("vid") int vid){
        if(vid ==0){
            return Result.error();
        }
        return videoInfoService.getInfo(vid);
    }

    @GetMapping("/getListByUid")
    public Result getListByUid(@RequestParam("uid") int uid){
        return videoInfoService.getListByUid(uid);
    }

    @PostMapping("/submit")
    public Result preUpload(Video video){
        return videoInfoService.submit(video);
    }

    @GetMapping("getVideoList")
    public  Result getVideoList(@RequestParam("num") int num,
                                @RequestParam("part") int part){

        return videoInfoService.getVideoList(num,part);
    }
}
