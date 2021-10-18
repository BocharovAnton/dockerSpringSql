package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, String> {
    AttendanceEntity findById(Long id);
}