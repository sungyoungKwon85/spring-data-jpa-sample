package com.kkwonsy.example.session.core.playhistory.redis.service;


import java.io.Serializable;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.kkwonsy.example.session.core.playhistory.model.PlayHistoryKey;
import com.kkwonsy.example.session.core.playhistory.redis.repository.PlayHistoryRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReactiveRedisServiceImpl<T extends Serializable> implements PlayHistoryRepository<T> {

    private static final Duration DEFAULT_DURATION = Duration.ofMinutes(10);

    private ReactiveRedisTemplate<PlayHistoryKey, T> reactiveRedisTemplate;

    @Autowired
    public ReactiveRedisServiceImpl(ReactiveRedisTemplate reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    public <S extends T> void save(PlayHistoryKey id, S data) {
        reactiveRedisTemplate.opsForValue()
            .set(id, data, DEFAULT_DURATION)
            .log();
    }

    @Override
    public <S extends T> void save(PlayHistoryKey id, S data, Duration duration) {
        reactiveRedisTemplate.opsForValue()
            .set(id, data, duration)
            .log();
    }

    @Override
    public Optional<T> findById(PlayHistoryKey id) {
        // todo
        Mono<T> obj = reactiveRedisTemplate.opsForValue().get(id);
        return Optional.empty();
    }

    @Override
    public void deleteOne(PlayHistoryKey id) {
        reactiveRedisTemplate.opsForValue()
            .delete(id)
            .log("deleted by " + id, Level.INFO);
    }
}
