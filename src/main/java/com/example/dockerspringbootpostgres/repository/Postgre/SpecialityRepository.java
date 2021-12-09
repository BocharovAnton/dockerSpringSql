package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, String> {
    Speciality findById(int id);
}