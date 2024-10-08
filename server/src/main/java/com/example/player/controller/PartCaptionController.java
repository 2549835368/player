package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.PartCaption;
import com.example.player.service.PartCaptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partCaption")
public class PartCaptionController {

    @Autowired
    PartCaptionService partCaptionService;

    @PostMapping("/save")
    public Result save(@RequestBody PartCaption partCaption){
        return partCaptionService.save(partCaption);
    }

    @GetMapping("/getList")
    public Result getList(){
        return partCaptionService.getList();
    }

    @GetMapping("/getOne")
    public Result getOne(@RequestParam("code") int code){
        return partCaptionService.getOne(code);
    }
}
