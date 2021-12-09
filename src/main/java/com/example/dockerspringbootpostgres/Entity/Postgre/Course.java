package com.example.dockerspringbootpostgres.Entity.Postgre;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "size", nullable = false)
    private int size;

    @OneToOne(targetEntity = Speciality.class)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    @OneToMany(targetEntity = Subject.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Subject> subjectList;

}