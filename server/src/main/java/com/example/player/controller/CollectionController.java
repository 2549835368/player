package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.Collection;
import com.example.player.entity.CollectionVideo;
import com.example.player.service.CollectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @PostMapping("/save")
    public Result save(@RequestBody CollectionVideo collectionVideo){
        return collectionService.save(collectionVideo);
    }

    @PostMapping("/createCollection")
    public Result createCol(@RequestBody Collection collection, HttpServletRequest request){
        return collectionService.createCol(collection,request);
    }

    @GetMapping("/get")
    public Result get(@RequestParam("cid") int cid){
        return collectionService.get(cid);
    }

    @GetMapping("/getByUid")
    public Result getByUid(@RequestParam("uid") int uid){
        return collectionService.getByUid(uid);
    }

    @GetMapping("/delCollection")
    public Result del(@RequestParam("cid") int cid,HttpServletRequest request){
        return collectionService.delCollection(cid,request);
    }

    @GetMapping("/getCollectionVideoByVid")
    public Result getCollectionByVid(@RequestParam("vid") int vid){
        return collectionService.getCollectionVideoByVid(vid);
    }

    public Result createCollectionVideo(CollectionVideo collectionVideo){
        return collectionService.createCollectionVideo(collectionVideo);
    }
}
