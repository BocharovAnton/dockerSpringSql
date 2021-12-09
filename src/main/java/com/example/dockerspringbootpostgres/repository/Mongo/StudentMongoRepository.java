package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.StudentMongo;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<StudentMongo, Integer> {
}
