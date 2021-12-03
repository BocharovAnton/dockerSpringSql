package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.GroupMongo;
import com.example.dockerspringbootpostgres.Entity.StudentMongo;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface GroupMongoRepository extends org.springframework.data.mongodb.repository.MongoRepository<GroupMongo, Integer> {
}
