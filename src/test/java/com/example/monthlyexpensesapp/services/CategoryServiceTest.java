package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.models.Category;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryServiceTest {
    
    @Test
    void throwingIllegalStateExc_ifCategoryalreadyExist() {
        //given
        var repo = mock(CategoryRepository.class);
        var category = new Category();
        category.setCategory_name("test_category");
        //then
        assertThat(category.getCategory_name()).isEqualTo("test_category");
        when(repo.existByName("test_category")).thenReturn(true);
        //udner test
        assertThrows(IllegalStateException.class,() -> new CategoryService(repo).addNewCategory("test_category"));
    }
    @Test
    void ifCategoryWithNameDoesNotExist_newCategoryWillBeAdded() {
        //given
        var repo = mock(CategoryRepository.class);
        var category = new Category();
        category.setCategory_name("test_category");
        //then
       
        when(repo.existByName("test_category")).thenReturn(false);
        when(repo.save(new Category())).thenReturn(category);
        assertThat(category.getCategory_name()).isEqualTo("test_category");
        //udner test
        assertDoesNotThrow(() -> new CategoryService(repo).addNewCategory("test_category"));
        
    }
    
}