package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, String> {
    SubjectEntity findById(Long id);
}