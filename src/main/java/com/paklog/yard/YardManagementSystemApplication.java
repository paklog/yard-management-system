package com.paklog.yard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableMongoAuditing
@EnableScheduling
public class YardManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(YardManagementSystemApplication.class, args);
    }
}
