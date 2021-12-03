package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.StudentMongo;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface StudentMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<StudentMongo, Integer> {
}
