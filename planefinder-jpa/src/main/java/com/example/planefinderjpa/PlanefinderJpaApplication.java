package com.example.planefinderjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlanefinderJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanefinderJpaApplication.class, args);
    }

}
