package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.TimetableEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "timetable")
@Getter
@Setter
@ToString
public class TimetableEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "datetime", nullable = false)
    private Timestamp date;

    @OneToOne(targetEntity = LectureEntity.class)
    @JoinColumn(name = "lecture_id")
    private LectureEntity lecture;

    @OneToMany(targetEntity = AttendanceEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AttendanceEntity> attendanceList;

}