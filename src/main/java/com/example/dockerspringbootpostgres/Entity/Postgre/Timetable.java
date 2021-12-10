package com.example.dockerspringbootpostgres.Entity.Postgre;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "timetable")
@Getter
@Setter
@ToString
public class Timetable {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime date;

    @OneToOne(targetEntity = Lecture.class)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(targetEntity = Group.class)
    private Set<Group> groupList;

    @OneToMany(targetEntity = Attendance.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Attendance> attendanceList;

}