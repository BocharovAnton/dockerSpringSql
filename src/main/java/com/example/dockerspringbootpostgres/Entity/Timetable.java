package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
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

    @OneToOne(targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(targetEntity = Attendance.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attendance> attendanceList;

}