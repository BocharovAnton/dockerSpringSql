package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Postgre.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<CourseMongo, Integer> {
}
