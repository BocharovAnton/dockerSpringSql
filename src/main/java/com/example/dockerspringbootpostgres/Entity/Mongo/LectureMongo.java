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
public class LectureMongo {
    @Id
    private int id;
    @Field("name")
    private String name;

    @Override
    public String toString() {
        return String.format(
                "Lectures[name='%s']", name);
    }
}
