package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.CategoryController;
import com.example.monthlyexpensesapp.category.CategoryRepository;
import com.example.monthlyexpensesapp.category.CategoryService;
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