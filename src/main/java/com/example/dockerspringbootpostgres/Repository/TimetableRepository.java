package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<TimetableEntity, String> {
    TimetableEntity findById(Long id);
}