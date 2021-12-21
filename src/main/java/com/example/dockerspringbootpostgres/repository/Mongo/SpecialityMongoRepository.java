package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SpecialityMongo;

import java.util.Set;

public interface SpecialityMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<SpecialityMongo, Integer> {
}
