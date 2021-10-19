package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureEntity, String> {
    LectureEntity findById(Long id);
}