package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<SpecialityEntity, String> {
    SpecialityEntity findById(Long id);
}