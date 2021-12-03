package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, String> {
    Subject findById(int id);
}