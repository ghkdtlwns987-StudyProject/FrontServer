package com.front.studyprojectfrontserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StudyProjectFrontServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyProjectFrontServerApplication.class, args);
    }
}
