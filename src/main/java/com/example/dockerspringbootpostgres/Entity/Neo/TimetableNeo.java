package com.example.dockerspringbootpostgres.Entity.Neo;

import com.example.dockerspringbootpostgres.Entity.Neo.AttendanceNeo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Timetable")
@Setter
@Getter
public class TimetableNeo {
    @Id
    Integer id;

    @Relationship(type = "_CONTAINS", direction = Relationship.Direction.OUTGOING)
    private Set<AttendanceNeo> attendanceList = new HashSet<>();
}
