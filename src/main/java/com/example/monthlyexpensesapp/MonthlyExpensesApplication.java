package com.example.monthlyexpensesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "com.example.monthlyexpensesapp.category",
        "com.example.monthlyexpensesapp.account",
        "com.example.monthlyexpensesapp.bill",
        "com.example.monthlyexpensesapp.shop",
        "com.example.monthlyexpensesapp.product"
})
public class MonthlyExpensesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonthlyExpensesApplication.class, args);
    }

}
