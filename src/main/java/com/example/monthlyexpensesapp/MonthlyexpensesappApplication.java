package com.example.monthlyexpensesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.monthlyexpensesapp.adapter")
public class MonthlyexpensesappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonthlyexpensesappApplication.class, args);
    }

}
