package com.kkwonsy.example.session.core.playhistory.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisRepositoryConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        if (StringUtils.isEmpty(redisProperties.getHost())) {
            redisProperties.setHost("localhost");
            redisProperties.setPort(6379);
        }
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }


    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        if (StringUtils.isEmpty(redisProperties.getHost())) {
            redisProperties.setHost("localhost");
            redisProperties.setPort(6379);
        }
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

//    @Bean
//    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
//
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//            .useSsl().and()
//            .commandTimeout(Duration.ofSeconds(2))
//            .shutdownTimeout(Duration.ZERO)
//            .build();
//
//        return new LettuceConnectionFactory(
//            new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
//    }

    @Bean
    public ReactiveRedisTemplate<?, ?> reactiveRedisTemplate( // reactiveJsonObjectRedisTemplate
        ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
//
//        RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
//            .newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, Object> serializationContext = builder
//            .value(new GenericJackson2JsonRedisSerializer("_type")).build();
//
//        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);

        ReactiveRedisTemplate<byte[], byte[]> reactiveRedisTemplate =
            new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, RedisSerializationContext.raw());

        return reactiveRedisTemplate;
    }
}
