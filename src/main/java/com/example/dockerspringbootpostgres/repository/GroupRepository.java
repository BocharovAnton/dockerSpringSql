package com.example.dockerspringbootpostgres.repository;

import com.example.dockerspringbootpostgres.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
    Group findById(int id);
}