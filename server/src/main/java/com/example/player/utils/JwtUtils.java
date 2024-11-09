package com.example.player.utils;

import com.example.player.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static long expire;

    private static String secret;


    @Value("${token.secret}")  // 从配置文件中读取secretKey的值
    public void setSecretKey(String secretKey) {
        secret = secretKey;
    }

    @Value("${token.expire}")  // 从配置文件中读取expireTime的值
    public void setExpireTime(long expireTime) {
        expire = expireTime;
    }

    //生成token
    public static String generateToken(String username){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //解析token
    public static Claims getClaimsByToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new ServiceException(500, "登录过期");
        }

        return claims;
    }

    //判断token是否存在
    public static boolean judgeTokenIsExist(String token) {
        Date date = JwtUtils.getClaimsByToken(token).getExpiration();
        Date now = new Date();
        return token != null && !token.isEmpty() && !"null".equals(token) && date.compareTo(now) > 0;
    }
}
