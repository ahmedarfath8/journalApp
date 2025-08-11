package com.arfath.journalApp.service;

import com.arfath.journalApp.api.responses.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    //geting
    public <T> T get(String key,Class<T> entityclass){ //Class<WeatherResponse> entityclass this we'll change to generic type
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper(); //this class will help us to convert object into pojo class
            return  mapper.readValue(o.toString(),entityclass);// //here were getting  so  readvalue instead of write  //this readvalue throws exception we must handle it
        } catch (Exception e) {
            log.error("Exception occured while getting",e);
            return null;
        }
    }

    //setter
    public void set(String key, Object o, Long ttl){ //Class<WeatherResponse> entityclass this we'll change to generic type
        try {
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(o); //here were setting so  write value
            redisTemplate.opsForValue().set(key,s,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception occured while setting",e);
        }
    }
}
