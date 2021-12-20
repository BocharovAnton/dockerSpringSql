package com.example.dockerspringbootpostgres.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@RedisHash("student")
@ToString
public class StudentRedis implements Serializable {

    Integer id;
    String fullName;


}