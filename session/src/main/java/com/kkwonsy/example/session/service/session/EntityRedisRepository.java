package com.kkwonsy.example.session.service.session;

import org.springframework.data.repository.CrudRepository;


public interface EntityRedisRepository extends CrudRepository<Entity, String> {

}
