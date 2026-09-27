package com.example.payment_application.services;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class IdempotencyService {

    private final StringRedisTemplate redisTemplate;

    private static final String LOCK_PREFIX = "idemp:lock:";

    public boolean acquireLock(String key, Duration ttl){
        Boolean success = redisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + key, "PROCESSING", ttl);
        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(String key){
        redisTemplate.delete(LOCK_PREFIX + key);
    }
}