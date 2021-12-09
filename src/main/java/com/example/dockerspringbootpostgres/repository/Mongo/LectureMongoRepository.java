package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<LectureMongo, Integer> {
}
