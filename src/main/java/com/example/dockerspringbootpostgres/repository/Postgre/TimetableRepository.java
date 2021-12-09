package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Lecture;
import com.example.dockerspringbootpostgres.Entity.Postgre.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, String> {
    List<Timetable> findAllByLectureAndDateBetween(Lecture lecture, LocalDateTime startDate, LocalDateTime endDate);
    Timetable findById(int id);
}