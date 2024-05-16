package com.derrick.big_event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testSet() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        operations.set("username", "derrick");
        operations.set("password", "123456", 15, TimeUnit.SECONDS);
    }

    @Test
    void testGet() {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        System.out.println(operations.get("username"));
    }
}
