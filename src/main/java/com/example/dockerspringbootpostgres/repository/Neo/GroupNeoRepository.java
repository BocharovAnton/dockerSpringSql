package com.example.dockerspringbootpostgres.repository.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.GroupNeo;

public interface GroupNeoRepository extends org.springframework.data.neo4j.repository.Neo4jRepository<GroupNeo, Integer>{
   GroupNeo findById(int id);

}
