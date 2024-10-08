package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videoInfo")
public class VideoInfoController {
    @Autowired
    VideoInfoService videoInfoService;

    @GetMapping("/getInfo/{vid}")
    public Result getInfo(@PathVariable int vid){
        if(vid ==0){
            return Result.error();
        }
        return videoInfoService.getInfo(vid);
    }
}
