package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.LectureEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lecture")
@Getter
@Setter
@ToString
public class LectureEntity {

    @Id
    @GeneratedValue
    private long id;


    @OneToOne(targetEntity = SubjectEntity.class)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;


    @OneToMany(targetEntity = TimetableEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TimetableEntity> timetableList;

}