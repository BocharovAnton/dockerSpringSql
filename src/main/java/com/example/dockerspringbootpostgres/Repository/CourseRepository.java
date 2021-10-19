package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, String> {
    CourseEntity findById(Long id);
}