package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "_group")
@Getter
@Setter
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;


    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @OneToOne(targetEntity = Speciality.class)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;


    @OneToMany(targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Student> studentsList;

    @OneToMany(targetEntity = Timetable.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Timetable> timeTableList;

}