package com.example.player;

import com.example.player.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Random;

@SpringBootTest
class PlayerApplicationTests {

	@Autowired
	RedisTemplate<Object,Object> redisTemplate;

	@Test
	void contextLoads() {
		Random random = new Random();
		int[] timeList = new int[4];
		timeList[0] = 1;
		for(int i = 1;i < 4; i++){
			timeList[i] =(random.nextInt(timeList[i-1],4000));
		}
		System.out.println(Arrays.toString(timeList));
	}

}
