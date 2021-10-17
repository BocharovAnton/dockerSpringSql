package com.example.dockerspringbootpostgres.Entity;

import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class StudentEntity {

    @Id
    @GeneratedValue
    private long id;


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "number", nullable = false)
    private int number;

    @OneToOne(targetEntity = GroupEntity.class)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

}