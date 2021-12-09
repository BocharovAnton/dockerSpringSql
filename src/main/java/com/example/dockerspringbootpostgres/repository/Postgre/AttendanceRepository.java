package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Attendance;
import com.example.dockerspringbootpostgres.Entity.Postgre.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    ArrayList<Attendance> findAllByTimetable(Timetable timetable);
}