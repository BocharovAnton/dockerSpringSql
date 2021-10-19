package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.SubjectEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@ToString
public class CourseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "size", nullable = false)
    private int size;

    @OneToOne(targetEntity = SpecialityEntity.class)
    @JoinColumn(name = "speciality_id")
    private SpecialityEntity speciality;


    @OneToMany(targetEntity = SubjectEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SubjectEntity> subjectList;

}