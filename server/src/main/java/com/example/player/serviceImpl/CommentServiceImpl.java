package com.example.player.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.player.bean.Result;
import com.example.player.entity.Comment;
import com.example.player.entity.User;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.CommentMapper;
import com.example.player.mapper.UserMapper;
import com.example.player.service.CommentService;
import com.example.player.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getCommentByVid(int vid,long current,long size) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("vid",vid).eq("pid",0);
        Page<Comment> page = new Page<>(current,size);

        page = commentMapper.selectPage(page,queryWrapper);

        for (Comment comment: page.getRecords()) {
            getChildComment(comment);
        }

        return Result.success(page);
    }

    @Override
    public Result getCommentByPid(int pid,long current,long size) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("pid",pid);
        Page<Comment> page = new Page<>(current,size);

        page = commentMapper.selectPage(page,queryWrapper);

        return Result.success(page);
    }

    @Override
    public Result saveComment(Comment comment, HttpServletRequest request) {
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            throw new ServiceException(500,"未登录");
        }
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User dbUser = userMapper.selectOne(queryWrapper);
        if(dbUser == null||dbUser.getId() != comment.getUid()){
            throw new ServiceException(500,"账号异常");
        }
        comment.setUid(dbUser.getId());
        commentMapper.insert(comment);
        return Result.success();
    }

    private void getChildComment(Comment comment){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("pid",comment.getId());
        Page<Comment> page = new Page<>(1,3);

        page = commentMapper.selectPage(page,queryWrapper);
        comment.setChildComment(page);

    }
}
