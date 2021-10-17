package com.example.dockerspringbootpostgres;

import java.util.List;

import com.example.dockerspringbootpostgres.Entity.StudentEntity;
import com.example.dockerspringbootpostgres.Repository.StudentRepository;
import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import com.example.dockerspringbootpostgres.Repository.GroupRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List allGroups = this.groupRepository.findAll();
        System.out.println("Number of Groups: " + allGroups.size());

        GroupEntity newGroup = new GroupEntity();
        newGroup.setGroupCode("BSBO-01-18");
        newGroup.setSpec("Information Technology");
        System.out.println(("Saving new Group..."));
        this.groupRepository.save(newGroup);


        allGroups = this.groupRepository.findAll();
        System.out.println("Number of Groups: " + allGroups.size());
        for(int i = 0; i<allGroups.size();i++){
            System.out.println("Group info:" + allGroups.get(i));
        }


        List allStudents = this.studentRepository.findAll();
        System.out.println("Number of Students: " + allStudents.size());

        StudentEntity newStudent = new StudentEntity();
        newStudent.setFirstName("John");
        newStudent.setLastName("Doe");
        newStudent.setNumber(12512);
        newStudent.setGroup(newGroup);
        System.out.println(("Saving new Student..."));
        this.studentRepository.save(newStudent);



        allStudents = this.studentRepository.findAll();
        System.out.println("Number of Students: " + allStudents.size());
        for(int i = 0; i<allStudents.size();i++){
            System.out.println("Student info:" + allStudents.get(i));
        }


        System.out.println(studentRepository.findAll());

    }
}
