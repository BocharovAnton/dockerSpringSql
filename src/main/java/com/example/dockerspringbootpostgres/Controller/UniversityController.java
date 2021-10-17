package com.example.dockerspringbootpostgres.Controller;

import com.example.dockerspringbootpostgres.Entity.GroupEntity;
import com.example.dockerspringbootpostgres.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import java.util.List;


@RestController
@RequestMapping(value="/api")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @GetMapping(value = "/getAllGroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupEntity> getAllGroups(){
        return universityService.getAllGroups();
    }
}
