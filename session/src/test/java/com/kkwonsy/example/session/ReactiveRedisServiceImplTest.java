package com.kkwonsy.example.session;

import java.time.Duration;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kkwonsy.example.session.core.sessionmanager.model.SessionKey;
import com.kkwonsy.example.session.core.sessionmanager.redis.ReactiveRedisServiceImpl;
import com.kkwonsy.example.session.model.SamplePojo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class ReactiveRedisServiceImplTest {

    @Autowired
    private ReactiveRedisServiceImpl<String> redisRepositoryString;

    @Autowired
    private ReactiveRedisServiceImpl<SamplePojo> redisRepositoryPojo;

    private final SessionKey id = SessionKey.builder()
        .play("pororo")
        .deviceId("nu110_2123")
        .customId("playlist")
        .build();

    @Test
    public void save_string() {
        redisRepositoryString.save(id, "drakaras");
        String saved = redisRepositoryString.findById(id).orElse("");
        assertThat(saved).isEqualTo("drakaras");
        redisRepositoryString.deleteOne(id);
    }

    @Test
    public void save_pojo1() {
        SamplePojo pojo = SamplePojo.builder().id(123).str("asdf").build();

        redisRepositoryPojo.save(id, pojo);
        SamplePojo saved = redisRepositoryPojo.findById(id).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");
        redisRepositoryPojo.deleteOne(id);
    }

    @Test
    public void save_with_duration_1mm() {
        SamplePojo pojo = SamplePojo.builder().id(123).str("asdf").build();

        redisRepositoryPojo.save(id, pojo, Duration.ofMillis(1));
        for (int i = 0; i < 100000; i++) {
        }
        SamplePojo saved = redisRepositoryPojo.findById(id).orElse(SamplePojo.builder().build());
        assertEquals(saved.getId(), null);
    }

    @Test
    public void update_string() {
        redisRepositoryString.save(id, "drakaras");
        String saved = redisRepositoryString.findById(id).orElse("");
        assertThat(saved).isEqualTo("drakaras");

        redisRepositoryString.save(id, "denerys");
        String updated = redisRepositoryString.findById(id).orElse("");
        assertThat(updated).isEqualTo("denerys");

        redisRepositoryPojo.deleteOne(id);
    }

    @Test
    public void update_pojo1() {
        SamplePojo pojo = SamplePojo.builder().id(123).str("asdf").build();

        redisRepositoryPojo.save(id, pojo);
        SamplePojo saved = redisRepositoryPojo.findById(id).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");

        saved.setStr("kkkkk");
        redisRepositoryPojo.save(id, saved);
        SamplePojo updated = redisRepositoryPojo.findById(id).orElse(SamplePojo.builder().build());
        assertThat(updated.getStr()).isEqualTo("kkkkk");

        redisRepositoryPojo.deleteOne(id);
    }


    @Test
    public void type_safe_test() {
        SamplePojo pojo = SamplePojo.builder().id(123).str("asdf").build();
        redisRepositoryPojo.save(id, pojo);

        // try to save a data of different type with the same id
        redisRepositoryString.save(id, "drakaras");
        // can't be changed to new type data
        SamplePojo saved = redisRepositoryPojo.findById(id).orElse(SamplePojo.builder().build());
        assertThat(saved.getStr()).isEqualTo("asdf");

        redisRepositoryPojo.deleteOne(id);
    }

}
