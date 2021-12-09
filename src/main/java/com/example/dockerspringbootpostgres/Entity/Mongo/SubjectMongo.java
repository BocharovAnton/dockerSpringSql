package com.example.dockerspringbootpostgres.Entity.Mongo;

import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
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
public class SubjectMongo {
    @Id
    private int id;
    @Field("name")
    private String name;
    @Field("lecture")
    Set<LectureMongo> lectureList;

    @Override
    public String toString() {
        return String.format(
                "Course[name='%s', lecture list='%s']", name, lectureList.toString());
    }


}
