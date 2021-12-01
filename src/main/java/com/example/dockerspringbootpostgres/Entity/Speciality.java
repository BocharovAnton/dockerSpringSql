package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "speciality")
public class Speciality {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = Course.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> CourseList;

    @OneToMany(targetEntity = Group.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Group> groupList;
}
