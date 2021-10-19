package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.SubjectEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter
@Setter
@ToString
public class SubjectEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = CourseEntity.class)
    @JoinColumn(name = "course_id")
    private CourseEntity course;


    @OneToMany(targetEntity = LectureEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<LectureEntity> lectureList;

}