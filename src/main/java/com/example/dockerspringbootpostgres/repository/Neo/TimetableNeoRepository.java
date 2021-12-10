package com.example.dockerspringbootpostgres.repository.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.LectureNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;

import java.util.Set;

public interface TimetableNeoRepository extends org.springframework.data.neo4j.repository.Neo4jRepository<TimetableNeo, Integer>{
    TimetableNeo findById(int id);
}
