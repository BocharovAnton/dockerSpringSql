package com.example.dockerspringbootpostgres.repository.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.GroupMongo;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<GroupMongo, Integer> {
}
