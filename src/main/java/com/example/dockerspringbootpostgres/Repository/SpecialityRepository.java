package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, String> {
    Speciality findById(int id);
}