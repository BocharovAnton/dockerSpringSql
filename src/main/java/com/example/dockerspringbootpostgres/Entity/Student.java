package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class Student {

    @Id
    @GeneratedValue
    private int id;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "number", nullable = false)
    private int number;

    @OneToOne(targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    private Group group;


    @OneToMany(targetEntity = Attendance.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attendance> attendanceList;

}