package com.example.dockerspringbootpostgres;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.Repository.*;

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
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository couseRepository;
    @Autowired
    private SpecialityRepository specialityRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        SpecialityEntity newSpeciality = new SpecialityEntity();
        newSpeciality.setName("Information Technology");
        this.specialityRepository.save(newSpeciality);

        CourseEntity newCourse = new CourseEntity();
        newCourse.setSpeciality(newSpeciality);
        newCourse.setSize(50);
        this.couseRepository.save(newCourse);


        SubjectEntity newSubject = new SubjectEntity();
        newSubject.setCourse(newCourse);
        newSubject.setName("Программирования");
        this.subjectRepository.save(newSubject);

        LectureEntity newLecture = new LectureEntity();
        newLecture.setSubject(newSubject);
        this.lectureRepository.save(newLecture);

        GroupEntity newGroup = new GroupEntity();
        newGroup.setGroupCode("BSBO-01-18");
        newGroup.setSpeciality(newSpeciality);
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
        newTimetable.setLecture(newLecture);
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
