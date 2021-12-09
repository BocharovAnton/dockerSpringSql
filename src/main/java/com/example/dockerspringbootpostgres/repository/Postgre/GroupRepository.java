package com.example.dockerspringbootpostgres.repository.Postgre;

import com.example.dockerspringbootpostgres.Entity.Postgre.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
    Group findById(int id);
}