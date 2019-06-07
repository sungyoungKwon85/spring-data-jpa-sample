package com.kkwonsy.example.session.service.session;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash("entity")
public class Entity implements Serializable {

    @Id
    private String id;
    private List<String> songs;

    @Builder
    public Entity(String id, List<String> songs) {
        this.id = id;
        this.songs = songs;
    }

    public void refresh(List<String> songs) {
        this.songs = songs;
    }
}
