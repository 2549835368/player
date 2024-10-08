package com.example.player;

import com.example.player.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class PlayerApplicationTests {

	@Autowired
	RedisTemplate<Object,Object> redisTemplate;

	@Test
	void contextLoads() {
//		String token = JwtUtils.generateToken("Liu");
//		System.out.print("token:");
//		System.out.println(token);
//
//		String sub = JwtUtils.getClaimsByToken(token).getSubject();
//		System.out.print("username:");
//		System.out.println(sub);

//		redisTemplate.opsForHash().put("1258","max",4);
//		redisTemplate.opsForHash().put("1258",1,1);

		String Range = "bytes=0-100";

		String[] range = Range.split("=");
		System.out.println(range[1]);

	}

}
