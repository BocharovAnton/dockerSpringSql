package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Attendance;
import com.example.dockerspringbootpostgres.Entity.Postgre.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    ArrayList<Attendance> findAllByTimetable(Timetable timetable);
    Attendance findById(Integer id);
    @Transactional
    @Modifying
    @Query(value = "CREATE TABLE IF NOT EXISTS attendance\n" +
            " ( id SERIAL,\n" +
            "  presence boolean,\n" +
            "  datetime timestamp,\n" +
            "  student_id int REFERENCES student,\n" +
            "  timetable_id int REFERENCES timetable,\n" +
            "  CONSTRAINT attendance_pk PRIMARY KEY ( id, datetime )\n"+
            " ) " +
            " PARTITION BY RANGE ( datetime );\n " +
            "CREATE TABLE IF NOT EXISTS attendance_1 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2021-09-01') TO ('2021-09-30');"+
            "CREATE TABLE IF NOT EXISTS attendance_2 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2021-10-01') TO ('2021-10-31');"+
            "CREATE TABLE IF NOT EXISTS attendance_3 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2021-11-01') TO ('2021-11-30');"+
            "CREATE TABLE IF NOT EXISTS attendance_4 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2021-12-01') TO ('2021-12-31');"+
            "CREATE TABLE IF NOT EXISTS attendance_5 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2022-01-01') TO ('2022-01-31');"+
            "CREATE TABLE IF NOT EXISTS attendance_6 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2022-02-01') TO ('2022-02-28');"+
            "CREATE TABLE IF NOT EXISTS attendance_7 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2022-03-01') TO ('2022-03-31');"+
            "CREATE TABLE IF NOT EXISTS attendance_8 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2022-04-01') TO ('2022-04-30');"+
            "CREATE TABLE IF NOT EXISTS attendance_9 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2022-05-01') TO ('2022-05-31');"+
            "CREATE TABLE IF NOT EXISTS attendance_10 PARTITION OF attendance \n"+
            "FOR VALUES FROM ('2021-05-01') TO ('2021-06-30');"
            ,nativeQuery = true)
    void createAttendanceTable();

}