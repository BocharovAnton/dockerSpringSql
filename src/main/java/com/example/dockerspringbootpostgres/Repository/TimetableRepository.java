package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.Lecture;
import com.example.dockerspringbootpostgres.Entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, String> {
    List<Timetable> findAllByLectureAndDateBetween(Lecture lecture, LocalDateTime startDate, LocalDateTime endDate);
    Timetable findById(int id);
}