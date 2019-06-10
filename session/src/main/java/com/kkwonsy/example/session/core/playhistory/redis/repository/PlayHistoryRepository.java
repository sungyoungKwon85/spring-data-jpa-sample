package com.kkwonsy.example.session.core.playhistory.redis.repository;

import java.time.Duration;
import java.util.Optional;

import com.kkwonsy.example.session.core.playhistory.model.PlayHistoryKey;

public interface PlayHistoryRepository<T> {

    <S extends T> void save(PlayHistoryKey id, S data);

    <S extends T> void save(PlayHistoryKey id, S data, Duration duration);

    Optional<T> findById(PlayHistoryKey id);

    void deleteOne(PlayHistoryKey id);
}
