package com.example.dockerspringbootpostgres;

import java.time.LocalDateTime;
import java.util.*;

import com.example.dockerspringbootpostgres.repository.Elastic.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SpecialityMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import com.example.dockerspringbootpostgres.Entity.Neo.AttendanceNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.LectureNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;
import com.example.dockerspringbootpostgres.Entity.Postgre.*;
import com.example.dockerspringbootpostgres.repository.*;

import com.example.dockerspringbootpostgres.Service.DataGenerator;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import com.example.dockerspringbootpostgres.repository.Mongo.*;
import com.example.dockerspringbootpostgres.repository.Neo.AttendanceNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.LectureNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.TimetableNeoRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;


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
    private UniversityService service;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private SpecialityMongoRepository specialityMongoRepository;
    @Autowired
    private CourseMongoRepository courseMongoRepository;
    @Autowired
    private SubjectMongoRepository subjectMongoRepository;
    @Autowired
    private LectureMongoRepository lectureMongoRepository;
    @Autowired
    private LectureNeoRepository lectureNeoRepository;
    @Autowired
    private AttendanceNeoRepository attendanceNeoRepository;
    @Autowired
    private TimetableNeoRepository timetableNeoRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
    }
}
