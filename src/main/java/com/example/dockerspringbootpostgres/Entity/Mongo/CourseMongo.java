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
public class CourseMongo {
    @Id
    private int id;
    @Field("size")
    private Integer size;
    @Field("subject")
    Set<SubjectMongo> subjectList;

    @Override
    public String toString() {
        return String.format(
                "[size='%s', subject list='%s']", size, subjectList.toString());
    }


}
