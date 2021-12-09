package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<SubjectMongo, Integer> {
}
