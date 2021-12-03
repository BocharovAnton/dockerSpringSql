package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Student;
import com.example.dockerspringbootpostgres.Entity.StudentRedis;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface RedisRepository {
    Map<String, StudentRedis> findAllStudents();
    void save(StudentRedis student);
    void delete(String  id);
    StudentRedis findStudentById(String id);
}
