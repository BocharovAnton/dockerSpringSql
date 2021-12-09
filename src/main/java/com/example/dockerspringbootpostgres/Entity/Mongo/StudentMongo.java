package com.example.dockerspringbootpostgres.Entity.Mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class StudentMongo {
    @Id
    private int id;
    @Field("name")
    private String fullName;
    @Override
    public String toString() {
        return String.format(
                "Student[code='%s', name='%s']", id, fullName);
    }
}
