package com.example.dockerspringbootpostgres.repository.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.StudentNeo;

public interface StudentNeoRepository extends org.springframework.data.neo4j.repository.Neo4jRepository<StudentNeo, Integer>{
   StudentNeo findById(int id);
}
