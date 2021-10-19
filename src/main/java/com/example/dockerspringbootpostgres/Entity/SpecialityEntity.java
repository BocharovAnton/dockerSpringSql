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
public class SpecialityEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = CourseEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CourseEntity> CourseList;

    @OneToMany(targetEntity = GroupEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<GroupEntity> groupList;
}
