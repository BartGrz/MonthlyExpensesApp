package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;


    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
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
    @PostMapping("/category")
    ResponseEntity<Category> createCategory(@RequestParam("category_name") String name) {
       
       Category category =  categoryService.addNewCategory(name);
        return ResponseEntity.created(URI.create("/"+category.getId_category())).body(category);

    }

    @PutMapping("/category/{id}")
    ResponseEntity<Category> updateCategory (@RequestBody Category toUpdate, @PathVariable int id) {

        if(!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.findById(id).ifPresent(category -> {

         category.updateFrom(toUpdate);
         categoryRepository.save(category);
        });
        return ResponseEntity.noContent().build();
    }

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
