package com.example.dockerspringbootpostgres.repository.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.AttendanceNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;

import java.util.Set;

public interface AttendanceNeoRepository extends org.springframework.data.neo4j.repository.Neo4jRepository<AttendanceNeo, Integer>{
   AttendanceNeo findById(int id);

}
