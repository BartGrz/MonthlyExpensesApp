package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.services.CategoryService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryModificationController {
    
    private CategoryService categoryService;
    
    public CategoryModificationController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/categories")
    public String addCategory(Model model) {
        model.addAttribute("category",new Category());
        return "categories";
    }
    @GetMapping("/delete-category")
    public String deleteCategory(Model model) {
        var categories = categoryService.getCategories();
        model.addAttribute("categories",categories);
        return "deleteCategory";
    }
    
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> illegalStateHandler(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
    
}
