package com.example.dockerspringbootpostgres;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import com.example.dockerspringbootpostgres.ElasticRepository.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.Repository.*;

import com.example.dockerspringbootpostgres.Service.UniversityService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootApplication
public class Application {

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
    private LectureFullTextRepository lectureFullTextRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SpecialityRepository specialityRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private UniversityService service;
    @Autowired
    private RestHighLevelClient client;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
        System.out.println("111");
        entryData();
        System.out.println("-------------------");
        lab1();
    }
    HashMap<Integer, Integer> studentsAttendance= new HashMap<>();
    public void lab1(){
        List<LectureFullText> lectureFullTextList = service.elasticSearch("несет в себе");
        for(LectureFullText lft:lectureFullTextList) {
            Lecture lecture = lectureRepository.findById(lft.getId());
            List <Timetable> timetableList = timetableRepository.findAllByLectureAndDateBetween(lecture, LocalDateTime.of(2021, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 0, 0));
            for(Timetable ttl :timetableList) {
                List<Attendance> attendanceList = attendanceRepository.findAllByTimetable(ttl);
                for(Attendance al:attendanceList) {
                    if(al.getPresence()==true){
                        if(studentsAttendance.get(al.getStudent()) != null) {
                            studentsAttendance.put(al.getStudent().getId(), studentsAttendance.get(al.getStudent()) + 1);
                        }
                        else{
                            studentsAttendance.put(al.getStudent().getId(), 1);
                        }
                    }
                    else{
                        studentsAttendance.put(al.getStudent().getId(), 0);
                    }
                }
            }
        }
        System.out.println(studentsAttendance);
        TreeMap<Integer, Integer> sorted = new TreeMap<>();
        sorted.putAll(studentsAttendance);
        System.out.println(sorted);
    }
    public void entryData(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        Speciality newSpeciality = new Speciality();
        newSpeciality.setName("Information Technology");
        this.specialityRepository.save(newSpeciality);

        Course newCourse = new Course();
        newCourse.setSpeciality(newSpeciality);
        newCourse.setSize(50);
        this.courseRepository.save(newCourse);


        Subject newSubject = new Subject();
        newSubject.setCourse(newCourse);
        newSubject.setName("Программирования");
        this.subjectRepository.save(newSubject);

        Lecture newLecture = new Lecture();
        newLecture.setSubject(newSubject);
        this.lectureRepository.save(newLecture);

        Lecture newLecture1 = new Lecture();
        newLecture1.setSubject(newSubject);
        this.lectureRepository.save(newLecture1);


        LectureFullText newFullTextLecture = new LectureFullText();
        newFullTextLecture.id = newLecture.getId();
        newFullTextLecture.text = "Полный текст лекции несет в себе много полезной информации";
        this.lectureFullTextRepository.save(newFullTextLecture);

        LectureFullText newFullTextLecture1 = new LectureFullText();
        newFullTextLecture1.id = newLecture1.getId();
        newFullTextLecture1.text = "Полный текст лекции несет в себе много полезной информации";
        this.lectureFullTextRepository.save(newFullTextLecture1);




        Group newGroup = new Group();
        newGroup.setGroupCode("BSBO-01-18");
        newGroup.setSpeciality(newSpeciality);
        System.out.println(("Saving new Group..."));
        this.groupRepository.save(newGroup);


        Student newStudent = new Student();
        newStudent.setFirstName("John");
        newStudent.setLastName("Doe");
        newStudent.setNumber(12512);
        newStudent.setGroup(newGroup);
        System.out.println(("Saving new Student..."));
        this.studentRepository.save(newStudent);

        Student newStudent1 = new Student();
        newStudent1.setFirstName("Anton");
        newStudent1.setLastName("Bocharov");
        newStudent1.setNumber(12513);
        newStudent1.setGroup(newGroup);
        System.out.println(("Saving new Student..."));
        this.studentRepository.save(newStudent1);

        Student newStudent2 = new Student();
        newStudent2.setFirstName("Dmitry");
        newStudent2.setLastName("Teryaev");
        newStudent2.setNumber(12514);
        newStudent2.setGroup(newGroup);
        System.out.println(("Saving new Student..."));
        this.studentRepository.save(newStudent2);


        Timetable newTimetable = new Timetable();
        newTimetable.setDate(LocalDateTime.now());
        newTimetable.setLecture(newLecture);
        System.out.println(("Saving new Attendance..."));
        this.timetableRepository.save(newTimetable);

        Timetable newTimetable1 = new Timetable();
        newTimetable1.setDate(LocalDateTime.now());
        newTimetable1.setLecture(newLecture1);
        System.out.println(("Saving new Attendance..."));
        this.timetableRepository.save(newTimetable1);

        Attendance newAttendance = new Attendance();
        newAttendance.setPresence(true);
        newAttendance.setStudent(newStudent);
        newAttendance.setTimetable(newTimetable);
        System.out.println(("Saving new Attendance..."));
        this.attendanceRepository.save(newAttendance);

        Attendance newAttendance1 = new Attendance();
        newAttendance1.setPresence(false);
        newAttendance1.setStudent(newStudent1);
        newAttendance1.setTimetable(newTimetable);
        System.out.println(("Saving new Attendance..."));
        this.attendanceRepository.save(newAttendance1);

        Attendance newAttendance2 = new Attendance();
        newAttendance2.setPresence(true);
        newAttendance2.setStudent(newStudent2);
        newAttendance2.setTimetable(newTimetable1);
        System.out.println(("Saving new Attendance..."));
        this.attendanceRepository.save(newAttendance2);

        System.out.println(studentRepository.findAll());
        System.out.println(groupRepository.findAll());
        System.out.println(attendanceRepository.findAll());
        System.out.println(lectureFullTextRepository.findAll());

    }

}
