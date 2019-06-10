package com.kkwonsy.example.session.core.playhistory.redis.service;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kkwonsy.example.session.core.playhistory.model.PlayHistoryKey;
import com.kkwonsy.example.session.core.playhistory.redis.repository.PlayHistoryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisServiceImpl<T extends Serializable> implements PlayHistoryRepository<T> {

    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(10);

    private final RedisTemplate<PlayHistoryKey, T> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public <S extends T> void save(PlayHistoryKey key, S data) {
        redisTemplate.opsForValue().set(key, data, DEFAULT_DURATION);
        log.debug("saved {}", data.toString());
    }

    @Override
    public <S extends T> void save(PlayHistoryKey key, S data, Duration duration) {
        redisTemplate.opsForValue().set(key, data, duration);
        log.debug("saved {}", data.toString());
    }

    @Override
    public Optional<T> findById(PlayHistoryKey key) {
        Optional<T> obj = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        log.debug("found {}", obj.toString());
        return obj;
    }

    @Override
    public void deleteOne(PlayHistoryKey key) {
        redisTemplate.delete(key);
        log.debug("deleted by id: {}", key);
    }

}
