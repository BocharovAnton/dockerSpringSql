package com.example.dockerspringbootpostgres;

import com.example.dockerspringbootpostgres.repository.RedisRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example.dockerspringbootpostgres"}, scanBasePackageClasses = RedisRepository.class)
public class DockerSpringBootPostgresApplication {
	public static void main(String[] args) {
		SpringApplication.run(DockerSpringBootPostgresApplication.class, args);
	}

}
