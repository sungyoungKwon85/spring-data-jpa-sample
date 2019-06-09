package com.kkwonsy.example.session.core.sessionmanager.redis;

import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.kkwonsy.example.session.core.sessionmanager.model.SessionKey;
import com.kkwonsy.example.session.core.sessionmanager.redis.domain.SessionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedisServiceImpl<T extends Serializable> implements SessionRepository<T> {

    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(10);

    private final RedisTemplate<SessionKey, T> redisTemplate;

    @Autowired
    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public <S extends T> void save(SessionKey key, S data) {
        redisTemplate.opsForValue().set(key, data, DEFAULT_DURATION);
        log.debug("saved {}", data.toString());
    }

    @Override
    public <S extends T> void save(SessionKey key, S data, Duration duration) {
        redisTemplate.opsForValue().set(key, data, duration);
        log.debug("saved {}", data.toString());
    }

    @Override
    public Optional<T> findById(SessionKey key) {
        Optional<T> obj = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        log.debug("found {}", obj.toString());
        return obj;
    }

    @Override
    public void deleteOne(SessionKey key) {
        redisTemplate.delete(key);
        log.debug("deleted by id: {}", key);
    }

}
