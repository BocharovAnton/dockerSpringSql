package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {
    StudentEntity findById(Long id);
}