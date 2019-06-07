package com.kkwonsy.example.session.core.sessionmanager.redis.domain;

import java.time.Duration;
import java.util.Optional;

public interface SessionRepository<T> {

    <S extends T> S save(String id, S data);

    <S extends T> S save(String id, S data, Duration duration);

    <S extends T> S update(String id, S data);

    <S extends T> S update(String id, S data, Duration duration);

    Optional<T> findById(String id);

    void deleteOne(String id);
}
