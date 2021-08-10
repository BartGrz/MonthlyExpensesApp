package com.example.monthlyexpensesapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/main")
public class MainPageController {

    @GetMapping
    public String mainPage() {
        return "home";
    }
}
