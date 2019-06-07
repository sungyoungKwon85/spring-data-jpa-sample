package com.kkwonsy.example.session.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

//    private EntityRedisRepository redisRepository;

//    @Autowired
//    public RedisController(EntityRedisRepository redisRepository) {
//        this.redisRepository = redisRepository;
//    }
//
//    @GetMapping(value = "/user")
//    public Entity getUser(@RequestParam String id) {
//        return redisRepository.findById(id).orElse(Entity.builder().build());
//    }

}
