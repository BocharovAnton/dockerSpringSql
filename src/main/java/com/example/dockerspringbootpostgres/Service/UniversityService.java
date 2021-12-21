package com.example.dockerspringbootpostgres.Service;

import com.example.dockerspringbootpostgres.Entity.*;
import com.example.dockerspringbootpostgres.Entity.Elastic.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Mongo.CourseMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.LectureMongo;
import com.example.dockerspringbootpostgres.Entity.Mongo.SubjectMongo;
import com.example.dockerspringbootpostgres.Entity.Neo.GroupNeo;
import com.example.dockerspringbootpostgres.Entity.Neo.TimetableNeo;
import com.example.dockerspringbootpostgres.Entity.Postgre.Course;
import com.example.dockerspringbootpostgres.Entity.Postgre.Group;
import com.example.dockerspringbootpostgres.repository.*;
import com.example.dockerspringbootpostgres.repository.Elastic.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.CourseMongoRepository;
import com.example.dockerspringbootpostgres.repository.Mongo.SpecialityMongoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.GroupNeoRepository;
import com.example.dockerspringbootpostgres.repository.Neo.TimetableNeoRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.AttendanceRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.GroupRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.LectureRepository;
import com.example.dockerspringbootpostgres.repository.Postgre.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@Service
public class UniversityService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private TimetableRepository timeTableRepository;
    @Autowired
    private LectureFullTextRepository lectureFullTextRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private TimetableNeoRepository timetableNeoRepository;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private CourseMongoRepository courseMongoRepository;
    @Autowired
    private GroupNeoRepository groupNeoRepository;
    @Autowired
    private SpecialityMongoRepository specialityMongoRepository;
    @Transactional
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<LectureFullText> elasticSearch(String textEntry){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("text", textEntry))
                .build();
        return elasticsearchRestTemplate.search(searchQuery, LectureFullText.class)
                .getSearchHits()
                .stream()
                .map(SearchHit::getContent).collect(Collectors.toList());
    }
    @Transactional
    public void saveStudent(StudentRedis student) {
        redisRepository.save(student);
    }
    @Transactional(readOnly = true)
    public Map<String, StudentRedis> getAllStudents() {
        return redisRepository.findAllStudents();
    }
    @Transactional
    public CourseMongo findByLecture(Optional<LectureMongo> arg_lecture){
        for(CourseMongo course: courseMongoRepository.findAll()){
            for(SubjectMongo subject: course.getSubjectList()){
                if(subject.getLectureList().contains(arg_lecture))
                    return course;
            }
        }
        return null;
    }
    @Transactional
    public GroupNeo findByGroupCode(String code){
        for (GroupNeo groupNeo : groupNeoRepository.findAll()) {
            if(groupNeo.getGroupCode().equals(code))
                return groupNeo;
        }
        return null;
    }
}

