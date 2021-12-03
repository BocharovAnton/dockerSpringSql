package com.example.dockerspringbootpostgres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@RedisHash("student")
@ToString
public class StudentRedis implements Serializable {

    private int id;
    private String fullName;


}