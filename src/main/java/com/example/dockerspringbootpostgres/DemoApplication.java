package com.example.dockerspringbootpostgres;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.example.dockerspringbootpostgres.Entity.StudentEntity;
import com.example.dockerspringbootpostgres.Entity.TimetableEntity;
import com.example.dockerspringbootpostgres.Repository.StudentRepository;
import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import com.example.dockerspringbootpostgres.Repository.GroupRepository;
import com.example.dockerspringbootpostgres.Entity.AttendanceEntity;
import com.example.dockerspringbootpostgres.Repository.AttendanceRepository;

import com.example.dockerspringbootpostgres.Repository.TimetableRepository;
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
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimetableRepository timetableRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        List allGroups = this.groupRepository.findAll();
        System.out.println("Number of Groups: " + allGroups.size());

        GroupEntity newGroup = new GroupEntity();
        newGroup.setGroupCode("BSBO-01-18");
        newGroup.setSpec("Information Technology");
        System.out.println(("Saving new Group..."));
        this.groupRepository.save(newGroup);


        StudentEntity newStudent = new StudentEntity();
        newStudent.setFirstName("John");
        newStudent.setLastName("Doe");
        newStudent.setNumber(12512);
        newStudent.setGroup(newGroup);
        System.out.println(("Saving new Student..."));
        this.studentRepository.save(newStudent);

        TimetableEntity newTimetable = new TimetableEntity();
        newTimetable.setDate(ts);
        System.out.println(("Saving new Attendance..."));
        this.timetableRepository.save(newTimetable);

        AttendanceEntity newAttendance = new AttendanceEntity();
        newAttendance.setPresence(true);
        newAttendance.setStudent(newStudent);
        newAttendance.setTimetable(newTimetable);
        System.out.println(("Saving new Attendance..."));
        this.attendanceRepository.save(newAttendance);

        System.out.println(studentRepository.findAll());
        System.out.println(groupRepository.findAll());
        System.out.println(attendanceRepository.findAll());

    }
}
