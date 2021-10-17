package com.example.dockerspringbootpostgres.Repository;

import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
    GroupEntity findById(Long id);
}