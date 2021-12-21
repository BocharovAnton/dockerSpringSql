package com.example.dockerspringbootpostgres.Entity.Neo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node("Group")
@Setter
@Getter
public class GroupNeo {
    @Id
    Integer id;
    String groupCode;

    @Relationship(type = "APPOINTED", direction = Relationship.Direction.OUTGOING)
    private Set<TimetableNeo> timetableList;

    @Relationship(type = "_CONTAINS", direction = Relationship.Direction.OUTGOING)
    private Set<StudentNeo> studentsList;

}
