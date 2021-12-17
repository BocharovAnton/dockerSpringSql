package com.example.dockerspringbootpostgres.Entity.Neo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Lecture")
@Setter
@Getter
public class LectureNeo {
    @Id
    Integer id;
    boolean special;
    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private Set<TimetableNeo> timetableList = new HashSet<>();
}
