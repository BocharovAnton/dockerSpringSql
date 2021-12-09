package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findById(int id);
}