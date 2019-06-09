package com.kkwonsy.example.session;

import java.time.Duration;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkwonsy.example.session.core.sessionmanager.model.SessionKey;
import com.kkwonsy.example.session.core.sessionmanager.redis.RedisServiceImpl;
import com.kkwonsy.example.session.model.SamplePojo;
import com.kkwonsy.example.session.service.session.Entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class RedisServiceImplTest {

    @Autowired
    private RedisServiceImpl<String> redisRepositoryString;

    @Autowired
    private RedisServiceImpl<Entity> redisRepositoryEntity;

    @Autowired
    private RedisServiceImpl<SamplePojo> redisRepositorySamplePojo;


    private final SessionKey redisKey = SessionKey.builder()
        .play("pororo")
        .deviceId("nu110_2123")
        .customId("playlist")
        .build();

    private final SamplePojo pojo = SamplePojo.builder()
        .id(123)
        .str("asdf")
        .build();

    private final Entity entity = Entity.builder()
        .id("idididid")
        .songs(Arrays.asList("aaa", "bbb"))
        .build();

    @Test
    public void save_string() {
        redisRepositoryString.save(redisKey, "drakaras");
        String saved = redisRepositoryString.findById(redisKey).orElse("");
        assertThat(saved).isEqualTo("drakaras");
        redisRepositoryString.deleteOne(redisKey);
    }

    @Test
    public void save_pojo1() {
        redisRepositorySamplePojo.save(redisKey, pojo);
        SamplePojo saved = redisRepositorySamplePojo.findById(redisKey).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");
        redisRepositorySamplePojo.deleteOne(redisKey);
    }

    @Test
    public void save_pojo2() {
        redisRepositoryEntity.save(redisKey, entity);
        Entity saved = redisRepositoryEntity.findById(redisKey).orElse(Entity.builder().build());
        assertThat(saved.getSongs()).isEqualTo(Arrays.asList("aaa", "bbb"));
        redisRepositoryEntity.deleteOne(redisKey);
    }

    @Test
    public void save_with_duration_1mm() {
        redisRepositoryEntity.save(redisKey, entity, Duration.ofMillis(1));
        for (int i = 0; i < 100000; i++) {
        }
        Entity saved = redisRepositoryEntity.findById(redisKey).orElse(Entity.builder().build());
        assertEquals(saved.getId(), null);
    }

    @Test
    public void update_string() {
        redisRepositoryString.save(redisKey, "drakaras");
        String saved = redisRepositoryString.findById(redisKey).orElse("");
        assertThat(saved).isEqualTo("drakaras");

        redisRepositoryString.save(redisKey, "denerys");
        String updated = redisRepositoryString.findById(redisKey).orElse("");
        assertThat(updated).isEqualTo("denerys");

        redisRepositoryString.deleteOne(redisKey);
    }

    @Test
    public void update_pojo1() {
        redisRepositorySamplePojo.save(redisKey, pojo);
        SamplePojo saved = redisRepositorySamplePojo.findById(redisKey).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");

        saved.setStr("kkkkk");
        redisRepositorySamplePojo.save(redisKey, saved);
        SamplePojo updated = redisRepositorySamplePojo.findById(redisKey).orElse(SamplePojo.builder().build());
        assertThat(updated.getStr()).isEqualTo("kkkkk");

        redisRepositorySamplePojo.deleteOne(redisKey);
    }


    @Test
    public void type_safe_test() {
        redisRepositorySamplePojo.save(redisKey, pojo);

        // try to save a data of different type with the same id
        redisRepositoryString.save(redisKey, "drakaras");
        // just changed as new type data
        String saved = redisRepositoryString.findById(redisKey).orElse("");
        assertThat(saved).isEqualTo("drakaras");

        redisRepositorySamplePojo.deleteOne(redisKey);
    }

}
