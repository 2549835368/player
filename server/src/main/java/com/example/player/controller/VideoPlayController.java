package com.example.player.controller;

import com.example.player.exception.ServiceException;
import com.example.player.mapper.VideoMapper;
import com.example.player.service.VideoPlayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/videoPlay")
public class VideoPlayController {

    @Value("${ip:localhost}")
    private String ip;

    @Value("${server.port}")
    private String port;

    @Autowired
    private VideoPlayService videoPlayService;

    @GetMapping("play/{id}")
    public void display(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
        videoPlayService.play(id,request,response);
    }

}
