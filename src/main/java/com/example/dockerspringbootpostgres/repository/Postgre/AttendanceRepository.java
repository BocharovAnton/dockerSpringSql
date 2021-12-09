package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Attendance;
import com.example.dockerspringbootpostgres.Entity.Postgre.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
//    Attendance findById(int id);
//    @Query(value = "SELECT * FROM Attendance WHERE timetable_id = :timetable",
//    nativeQuery = true)
//    ArrayList<Attendance> findByLectureId(@Param("timetable") Timetable timetable);
    ArrayList<Attendance> findAllByTimetable(Timetable timetable);
}