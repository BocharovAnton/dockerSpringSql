package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "_group")
@Getter
@Setter
public class GroupEntity {

    @Id
    @GeneratedValue
    private long id;


    @Column(name = "group_code", nullable = false)
    private String groupCode;

    @OneToOne(targetEntity = SpecialityEntity.class)
    @JoinColumn(name = "speciality_id")
    private SpecialityEntity speciality;


    @OneToMany(targetEntity = StudentEntity.class, cascade = CascadeType.ALL)
    private Set<StudentEntity> studentsList;

}