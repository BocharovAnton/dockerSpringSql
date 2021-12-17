package com.example.dockerspringbootpostgres.Entity.Postgre;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class Student implements Serializable {

    @Id
    private int id;

    @ToString.Exclude
    @OneToOne(targetEntity = Group.class)
    @JoinColumn(name = "group_id")
    private Group group;
}