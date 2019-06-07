package com.kkwonsy.example.session;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkwonsy.example.session.service.session.Entity;
import com.kkwonsy.example.session.service.session.EntityRedisRepository;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
@Ignore
public class RedisTest {


    @Autowired
    private EntityRedisRepository entityRedisRepository;

//    @After
//    public void tearDown() {
//        entityRedisRepository.deleteAll();
//    }


    @Test
    public void save() {
        String id = "kkwonsy";
        Entity entity = Entity.builder()
            .id(id)
            .songs(Arrays.asList("aaa", "bbb"))
            .build();

        entityRedisRepository.save(entity);

        Entity savedEntity = entityRedisRepository.findById(id).orElse(Entity.builder().build());
        assertThat(savedEntity.getSongs()).isEqualTo(Arrays.asList("aaa", "bbb"));

        entityRedisRepository.deleteById(id);
    }

    @Test
    public void update() {
        String id = "kkwonsy";
        Entity entity = Entity.builder()
            .id(id)
            .songs(Arrays.asList("aaa", "bbb"))
            .build();

        entityRedisRepository.save(entity);
        Entity savedEntity = entityRedisRepository.findById(id).orElse(Entity.builder().build());

        savedEntity.refresh(Arrays.asList("AAA", "BBB"));
        entityRedisRepository.save(savedEntity);
        Entity updatedEntity = entityRedisRepository.findById(id).orElse(Entity.builder().build());

        assertThat(updatedEntity.getSongs()).isEqualTo(Arrays.asList("AAA", "BBB"));

        entityRedisRepository.deleteById(id);
    }


}
