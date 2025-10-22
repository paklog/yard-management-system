package com.paklog.yard.management.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Yard Management System
 *
 * Dock door scheduling and trailer tracking management
 *
 * @author Paklog Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableKafka
@EnableMongoAuditing
public class YardManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(YardManagementSystemApplication.class, args);
    }
}