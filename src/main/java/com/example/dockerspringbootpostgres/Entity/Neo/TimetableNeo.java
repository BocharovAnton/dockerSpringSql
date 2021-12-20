package com.example.dockerspringbootpostgres.Entity.Neo;

import com.example.dockerspringbootpostgres.Entity.Postgre.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Node("Timetable")
@Setter
@Getter
public class TimetableNeo {
    @Id
    Integer id;

    LocalDateTime date;

    private Set<Integer> attendanceList;

    @Relationship(type = "BELONGS", direction = Relationship.Direction.OUTGOING)
    private LectureNeo lecture;

    @Relationship(type = "VISITS", direction = Relationship.Direction.OUTGOING)
    private Set<GroupNeo> groupList;
}
