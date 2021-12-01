package com.example.dockerspringbootpostgres.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "lecture")
@Getter
@Setter
@ToString
public class Lecture {
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Id
    private int id;


    @OneToOne(targetEntity = Subject.class)
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @OneToMany(targetEntity = Timetable.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Timetable> timetableList;

}