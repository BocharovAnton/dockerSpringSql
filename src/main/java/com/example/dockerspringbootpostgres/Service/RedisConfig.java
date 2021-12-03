package com.example.dockerspringbootpostgres.Service;

import com.example.dockerspringbootpostgres.Entity.Student;
import com.example.dockerspringbootpostgres.Entity.StudentRedis;
import com.example.dockerspringbootpostgres.repository.RedisRepository;
import com.example.dockerspringbootpostgres.repository.RedisRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableJpaRepositories(value = "com.example.dockerspringbootpostgres.repository")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, StudentRedis> redisTemplate() {
        RedisTemplate<String, StudentRedis> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RedisRepository redisRepository() {
        return new RedisRepositoryImpl(redisTemplate());
    }

}
