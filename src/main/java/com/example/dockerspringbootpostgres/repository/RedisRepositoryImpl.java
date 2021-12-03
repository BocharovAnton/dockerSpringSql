package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Student;
import com.example.dockerspringbootpostgres.Entity.StudentRedis;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

public class RedisRepositoryImpl implements RedisRepository{
    private static final String STUDENT_KEY = "student";
    private final RedisTemplate<String, StudentRedis> redisTemplate;
    private HashOperations<String, String, StudentRedis> hashOperations;

    public RedisRepositoryImpl(RedisTemplate<String, StudentRedis> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    @Override
    public void save(final StudentRedis student) {
        hashOperations.put(STUDENT_KEY, String.valueOf(student.getId())  , student);
    }

    @Override
    public void delete(final String id) {
        hashOperations.delete(STUDENT_KEY, id);
    }

    @Override
    public StudentRedis findStudentById(final String id){
        return hashOperations.get(STUDENT_KEY, id);
    }

    @Override
    public Map<String, StudentRedis> findAllStudents(){
        return hashOperations.entries(STUDENT_KEY);
    }
}
