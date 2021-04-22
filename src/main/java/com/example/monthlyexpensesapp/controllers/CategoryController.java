package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.models.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CategoryController {

    private CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
    ResponseEntity<Category> createCategory(@RequestBody Category toCreate) {

       Category category =  categoryRepository.save(toCreate);

        return ResponseEntity.created(URI.create("/"+category.getId_category())).body(toCreate);

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
    ResponseEntity<Account> deleteAccount (@PathVariable int id) {

        if(!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);

        return ResponseEntity.noContent().build();
    }
}
