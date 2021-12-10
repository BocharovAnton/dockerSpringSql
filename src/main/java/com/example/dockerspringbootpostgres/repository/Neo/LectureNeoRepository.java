package com.example.dockerspringbootpostgres.repository.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.LectureNeo;
import com.example.dockerspringbootpostgres.Entity.Postgre.Lecture;

import java.util.Set;

public interface LectureNeoRepository extends org.springframework.data.neo4j.repository.Neo4jRepository<LectureNeo, Integer>{
    LectureNeo findById(int id);
}
