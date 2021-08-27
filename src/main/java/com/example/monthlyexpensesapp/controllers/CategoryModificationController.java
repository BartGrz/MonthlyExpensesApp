package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryModificationController {
    
    private CategoryService categoryService;
    
    public CategoryModificationController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add-category")
    public String addCategory(Model model) {
        model.addAttribute("category",new Category());
        return "addCategory";
    }
    @GetMapping("/delete-category")
    public String deleteCategory(Model model) {
        model.addAttribute("categories",getCategories());
        return "deleteCategory";
    }
    @GetMapping("/edit-category")
    public String editCategory(Model model) {
        model.addAttribute("categories",getCategories());
        return "editCategory";
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> illegalStateHandler(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
