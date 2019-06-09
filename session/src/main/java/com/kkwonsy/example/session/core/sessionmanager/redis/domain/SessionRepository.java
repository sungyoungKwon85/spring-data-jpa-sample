package com.kkwonsy.example.session.core.sessionmanager.redis.domain;

import java.time.Duration;
import java.util.Optional;

import com.kkwonsy.example.session.core.sessionmanager.model.SessionKey;

public interface SessionRepository<T> {

    <S extends T> void save(SessionKey id, S data);

    <S extends T> void save(SessionKey id, S data, Duration duration);

//    <S extends T> S update(String id, S data);

//    <S extends T> S update(String id, S data, Duration duration);

    Optional<T> findById(SessionKey id);

    void deleteOne(SessionKey id);
}
