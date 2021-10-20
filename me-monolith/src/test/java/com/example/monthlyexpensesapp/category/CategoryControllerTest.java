package com.example.monthlyexpensesapp.category;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

class CategoryControllerTest {

    @Test
    void readById() {

            CategoryRepository categoryRepository = mock(CategoryRepository.class);
            var categoryService = mock(CategoryService.class);
            var categoryQueryRepository = mock(CategoryQueryRepository.class);
            CategoryController categoryController = new CategoryController(categoryRepository, categoryService, categoryQueryRepository);
            categoryController.readById(anyInt());

        }

    @Test
    void updateCategory() {



    }
}