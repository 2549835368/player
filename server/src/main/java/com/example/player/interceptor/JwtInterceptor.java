package com.example.player.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.player.entity.User;
import com.example.player.exception.ServiceException;
import com.example.player.mapper.UserMapper;
import com.example.player.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        if(StrUtil.isBlank(token)){
            System.err.println("请登录");
            throw new ServiceException("401","请登录");
        }
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        System.err.println(username);
        User user = userMapper.findByUsername(username);
        if(user == null){
            throw new ServiceException("401","请重新登录");
        }

        return true;
    }
}
