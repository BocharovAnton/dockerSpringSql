package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subject")
@Getter
@Setter
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id")
    private Course course;


    @OneToMany(targetEntity = Lecture.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Lecture> lectureList;

}