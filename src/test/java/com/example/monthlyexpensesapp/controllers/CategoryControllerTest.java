package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.services.CategoryService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

class CategoryControllerTest {

    @Test
    void readById() {

            CategoryRepository categoryRepository = mock(CategoryRepository.class);
            var categoryService = mock(CategoryService.class);
            CategoryController categoryController = new CategoryController(categoryRepository, categoryService);
            categoryController.readById(anyInt());

        }

    @Test
    void updateCategory() {



    }
}