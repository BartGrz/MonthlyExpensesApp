package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.services.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.net.URI;
import java.util.List;

@Controller
public class CategoryController {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;


    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }
    @GetMapping("/show-categories")
    String showCategories(Model model) {
        model.addAttribute("categories",categoryService.getCategories());
        return "showCategories";
    }

    @GetMapping("/category")
    ResponseEntity<List<Category>> readAll() {

        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/category/{id}")
    ResponseEntity<Category> readById (@PathVariable int id) {

        if(!categoryRepository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        Category category = categoryRepository.findById(id).get();
        return ResponseEntity.ok(category);
    }
    @ResponseBody
    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Category> createCategory(@RequestBody Category toCreate) {
       
       Category category =  categoryService.addNewCategory(toCreate);
        return ResponseEntity.created(URI.create("/"+category.getId_category())).body(toCreate);

    }
    @ResponseBody
    @PutMapping(value = "/category/{id}")
    ResponseEntity<Category> updateCategory (@RequestBody Category toUpdate, @PathVariable int id) {

       categoryService.updateCategory(id,toUpdate);

        return ResponseEntity.noContent().build();
    }
    @ResponseBody
    @DeleteMapping("/category/{id}")
    ResponseEntity<Category> deleteAccount (@PathVariable int id, Model model) {
    
            categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
 
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> illegalArgHandler(IllegalArgumentException e) {
     return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> illegalStateHandler(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
   
}
