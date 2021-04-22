package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.models.CategoryRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

class CategoryControllerTest {

    @Test
    void readById() {

            CategoryRepository categoryRepository = mock(CategoryRepository.class);
            CategoryController categoryController = new CategoryController(categoryRepository);
            categoryController.readById(anyInt());

        }

    @Test
    void updateCategory() {

        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        CategoryController categoryController = new CategoryController(categoryRepository);
        categoryController.updateCategory(mock(Category.class),anyInt());
        

    }
}