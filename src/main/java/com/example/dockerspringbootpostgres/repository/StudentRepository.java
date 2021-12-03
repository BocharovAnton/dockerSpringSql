package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findById(int id);
}