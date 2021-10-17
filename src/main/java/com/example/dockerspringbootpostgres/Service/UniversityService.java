package com.example.dockerspringbootpostgres.Service;

import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import com.example.dockerspringbootpostgres.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UniversityService {
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public List<GroupEntity> getAllGroups(){
        return groupRepository.findAll();
    }
}
