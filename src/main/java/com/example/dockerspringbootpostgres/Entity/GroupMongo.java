package com.example.dockerspringbootpostgres.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class GroupMongo {
    @Id
    private int id;
    @Field("code")
    private String code;
    @Field("studentlist")
    Set<StudentMongo> studentList;

    @Override
    public String toString() {
        return String.format(
                "Group[code='%s', lastName='%s']", code, studentList.toString());
    }


}
