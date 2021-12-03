package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findById(int id);
}