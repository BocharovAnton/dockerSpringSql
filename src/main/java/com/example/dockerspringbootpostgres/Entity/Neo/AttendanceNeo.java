package com.example.dockerspringbootpostgres.Entity.Neo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Attendance")
@Setter
@Getter
public class AttendanceNeo {
    @Id
    Integer id;
    Boolean presence;
    Integer student;
}
