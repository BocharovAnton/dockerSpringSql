package com.example.dockerspringbootpostgres.Entity.Neo;

import com.example.dockerspringbootpostgres.Entity.Postgre.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("Student")
@Setter
@Getter
public class StudentNeo {
    @Id
    Integer id;
    String fullName;

}
