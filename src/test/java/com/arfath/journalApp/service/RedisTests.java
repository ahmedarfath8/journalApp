package com.arfath.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;  //check redisConfig code comment for more info in config package

    @Disabled
    @Test
    public void redisConnectionTest(){
        redisTemplate.opsForValue().set("email","arfath@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object arfath = redisTemplate.opsForValue().get("arfath");

    }
}
