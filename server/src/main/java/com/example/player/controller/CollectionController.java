package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.Collection;
import com.example.player.mapper.CollectionMapper;
import com.example.player.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @PostMapping("/save")
    public Result save(@RequestBody Collection collection){
        return collectionService.save(collection);
    }

    @GetMapping("/get/{cid}")
    public Result get(@PathVariable int cid){
        return collectionService.get(cid);
    }

}
