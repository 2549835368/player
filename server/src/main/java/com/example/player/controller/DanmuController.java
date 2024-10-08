package com.example.player.controller;


import com.example.player.bean.Result;
import com.example.player.entity.Danmu;
import com.example.player.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/danmu")
public class DanmuController {

    @Autowired
    private DanmuService danmuService;

    @PostMapping("/v3/")
    @ResponseBody
    public Result addDanmu(@RequestBody Danmu danmu){
        int vid = danmu.getId();
        danmu.setId(0);
        danmu.setVid(vid);
        danmuService.save(danmu);
        return  new Result(0,"请求成功",danmu);
    }

    @GetMapping("/v3/")
    @ResponseBody
    public  Result getDanmus(@RequestParam("id") String vid){
        List<Object> list = danmuService.getDanmus(vid);
        return new Result(0,"请求成功",list);
    }
}
