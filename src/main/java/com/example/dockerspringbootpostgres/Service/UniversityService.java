package com.example.dockerspringbootpostgres.Service;

import com.example.dockerspringbootpostgres.Entity.Group;
import com.example.dockerspringbootpostgres.Entity.Lecture;
import com.example.dockerspringbootpostgres.Entity.LectureFullText;
import com.example.dockerspringbootpostgres.Entity.Timetable;
import com.example.dockerspringbootpostgres.Repository.AttendanceRepository;
import com.example.dockerspringbootpostgres.Repository.GroupRepository;
import com.example.dockerspringbootpostgres.ElasticRepository.LectureFullTextRepository;
import com.example.dockerspringbootpostgres.Repository.LectureRepository;
import com.example.dockerspringbootpostgres.Repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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
}

