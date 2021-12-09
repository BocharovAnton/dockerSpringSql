package com.example.dockerspringbootpostgres.Entity.Mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class SpecialityMongo {
    @Id
    private int id;
    @Field("name")
    private String name;
    @Field("courselist")
    Set<CourseMongo> courseList;

    @Override
    public String toString() {
        return String.format(
                "Speciality[name='%s', courses:='%s']", name, courseList.toString());
    }
}
