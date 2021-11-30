package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "_group")
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue
    private int id;


    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @OneToOne(targetEntity = Speciality.class)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL)
    private Set<Student> studentsList;

}