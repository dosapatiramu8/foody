package com.foody.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public int getNextIndex(String key, int maxIndex) {
        Long currentIndex = redisTemplate.opsForValue().increment(key);

        if (currentIndex == null || currentIndex >= maxIndex) {
            redisTemplate.opsForValue().set(key, "0");
            return 0;
        }
        return currentIndex.intValue();
    }
}
