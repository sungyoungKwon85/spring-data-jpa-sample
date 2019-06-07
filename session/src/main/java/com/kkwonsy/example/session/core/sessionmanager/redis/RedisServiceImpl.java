package com.kkwonsy.example.session.core.sessionmanager.redis;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kkwonsy.example.session.core.sessionmanager.redis.domain.SessionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisServiceImpl<T extends Serializable> implements SessionRepository<T> {

    static final Duration DEFAULT_DURATION = Duration.ofMinutes(10);

    private RedisTemplate redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public <S extends T> S save(String id, S data) {
        redisTemplate.opsForValue().setIfAbsent(id, data, DEFAULT_DURATION);
        return data;
    }

    @Override
    public <S extends T> S save(String id, S data, Duration duration) {
        redisTemplate.opsForValue().setIfAbsent(id, data, duration);
        return data;
    }

    @Override
    public <S extends T> S update(String id, S data) {
        redisTemplate.opsForValue().set(id, data, DEFAULT_DURATION);
        return data;
    }

    @Override
    public <S extends T> S update(String id, S data, Duration duration) {
        redisTemplate.opsForValue().set(id, data, duration);
        return data;
    }

    @Override
    public Optional<T> findById(String id) {
        Optional obj = Optional.ofNullable(redisTemplate.opsForValue().get(id));
        return obj;
    }

    @Override
    public void deleteOne(String id) {
        redisTemplate.delete(id);
    }
}
