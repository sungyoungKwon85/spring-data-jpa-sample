package com.kkwonsy.example.session;

import java.time.Duration;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkwonsy.example.session.core.sessionmanager.redis.RedisServiceImpl;
import com.kkwonsy.example.session.model.SamplePojo;
import com.kkwonsy.example.session.service.session.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class RedisTest2 {

    @Autowired
    private RedisServiceImpl<Entity> redisRepository;

    @Autowired
    private RedisServiceImpl<String> redisRepository2;

    @Autowired
    private RedisServiceImpl<SamplePojo> redisRepository3;


    @Test
    public void save_string() {
        String id = "kkwonsy";

        redisRepository2.save(id, "drakaras");
        String saved = redisRepository2.findById(id).orElse("");
        assertThat(saved).isEqualTo("drakaras");
        redisRepository.deleteOne(id);
    }

    @Test
    public void save_pojo1() {
        String id = "kkwonsy";

        SamplePojo pojo = SamplePojo.builder().id(123).str("asdf").build();

        redisRepository3.save(id, pojo);
        SamplePojo saved = redisRepository3.findById(id).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");
        redisRepository.deleteOne(id);
    }

    @Test
    public void save_pojo2() {
        String id = "kkwonsy";
        Entity entity = Entity.builder()
            .id(id)
            .songs(Arrays.asList("aaa", "bbb"))
            .build();

        redisRepository.save(id, entity);
        Entity saved = redisRepository.findById(id).orElse(Entity.builder().build());
        assertThat(saved.getSongs()).isEqualTo(Arrays.asList("aaa", "bbb"));
        redisRepository.deleteOne(id);
    }

    @Test
    public void save_with_duration_1mm() {
        String id = "kkwonsy";
        Entity entity = Entity.builder()
            .id(id)
            .songs(Arrays.asList("aaa", "bbb"))
            .build();

        redisRepository.save(id, entity, Duration.ofMillis(1));
        for (int i = 0; i < 100000; i++) {
        }
        Entity saved = redisRepository.findById(id).orElse(Entity.builder().build());
        assertEquals(saved.getId(), null);
    }


}
