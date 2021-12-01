package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@ToString
public class Attendance {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "presence", nullable = false)
    private Boolean presence;

    @OneToOne(targetEntity = Timetable.class)
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;


    @OneToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;

}