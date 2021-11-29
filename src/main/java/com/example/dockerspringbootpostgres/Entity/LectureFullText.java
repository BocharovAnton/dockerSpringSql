package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@ToString
@Setter
@Getter
@Document(indexName = "lecture")
public class LectureFullText {
    @Id
    public int id;
    public String text;
}