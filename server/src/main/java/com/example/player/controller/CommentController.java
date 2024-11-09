package com.example.player.controller;

import com.example.player.bean.Result;
import com.example.player.entity.Comment;
import com.example.player.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("getCommentByVid")
    public Result getCommentByVid(@RequestParam("vid") int vid,
                                  @RequestParam (value = "current",defaultValue = "1") long current,
                                  @RequestParam (value = "size",defaultValue = "5") long size){
        return commentService.getCommentByVid(vid,current,size);
    }

    @GetMapping("getCommentByPid")
    public Result getCommentByPid(@RequestParam("pid") int pid,
                                  @RequestParam (value = "current",defaultValue = "1") long current,
                                  @RequestParam (value = "size",defaultValue = "3") long size){
        return commentService.getCommentByPid(pid,current,size);
    }

    @PostMapping("save")
    public Result save(@RequestBody Comment comment, HttpServletRequest request){
        return commentService.saveComment(comment,request);
    }
}
