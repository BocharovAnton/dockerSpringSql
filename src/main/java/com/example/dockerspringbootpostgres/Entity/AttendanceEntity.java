package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@ToString
public class AttendanceEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "presence", nullable = false)
    private Boolean presence;

    @OneToOne(targetEntity = TimetableEntity.class)
    @JoinColumn(name = "timetable_id")
    private TimetableEntity timetable;


    @OneToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "student_id")
    private StudentEntity student;

}