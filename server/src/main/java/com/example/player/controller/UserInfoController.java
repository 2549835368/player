package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userinfoService;

    @GetMapping("getById")
    public Result getById(@RequestParam("id") int id){
        return userinfoService.getById(id);
    }

    @PostMapping("getNamesById")
    public Result getNameById(@RequestParam("idList") ArrayList<Integer> idList){
        return userinfoService.getNamesById(idList);
    }
}
