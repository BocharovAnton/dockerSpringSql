package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<CourseMongo, Integer> {
}
