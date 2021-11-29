package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.Group;
import com.example.dockerspringbootpostgres.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {
    Group findById(int id);

}