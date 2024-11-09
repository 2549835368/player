package com.example.player.service;

import com.example.player.bean.Result;
import com.example.player.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {
    Result getCommentByVid(int vid,long current,long size);

    Result getCommentByPid(int pid,long current,long size);

    Result saveComment(Comment comment, HttpServletRequest request);
}
