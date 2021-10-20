package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.CategoryRepository;
import com.example.monthlyexpensesapp.category.CategoryService;
import com.example.monthlyexpensesapp.category.Category;
import com.example.monthlyexpensesapp.category.dto.CategoryDto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        var saved = new Category();
        category.setCategory_name("test_category");
        //then
        assertThat(category.getCategory_name()).isEqualTo("test_category");
        when(repo.existByName("test_category")).thenReturn(true);
        when(repo.save(category)).thenReturn(saved);
        //udner test
        assertThrows(IllegalStateException.class,() -> new CategoryService(repo).addNewCategory(category));
    }
    @Test
    void ifCategoryWithNameDoesNotExist_newCategoryWillBeAdded() {
        //given
        var repo = mock(CategoryRepository.class);
        var category = new Category();
        category.setCategory_name("test_category");
        var saved = new Category();
        saved.setCategory_name(category.getCategory_name());

        //then
       
        when(repo.existByName("test_category")).thenReturn(false);
        when(repo.save(new Category())).thenReturn(category);
        when(repo.save(category)).thenReturn(saved);
        assertThat(saved.getCategory_name()).isEqualTo("test_category");
        //udner test
        assertDoesNotThrow(() -> new CategoryService(repo).addNewCategory(category));
        
    }
    @Test
    void throwingIllegalArgExc_ifCategoryWithIdGiven_DoesNotExist() throws NoSuchFieldException, IllegalAccessException {
        var repo = mock(CategoryRepository.class);
        var category = new Category();
        Field getId = category.getClass().getDeclaredField("id_category");
        getId.setAccessible(true);
        getId.set(category,1);
        category.setCategory_name("test_category");
        var saved = new Category();

        var toUpdate = new Category();
        toUpdate.setCategory_name("foobar");

        assertThat(category.getCategory_name()).isEqualTo("test_category");
        assertThat(category.getId_category()).isEqualTo(1);
        when(repo.existsById(1)).thenReturn(false);
        when(repo.save(category)).thenReturn(saved);
        assertThrows(IllegalArgumentException.class,() -> new CategoryService(repo).updateCategory(1,toUpdate));
    }
    @Test
    void updating_ifCategoryWithIdGiven_Exist() throws NoSuchFieldException, IllegalAccessException {
        var repo = mock(CategoryRepository.class);
        var category = new Category();
        Field getId = category.getClass().getDeclaredField("id_category");
        getId.setAccessible(true);
        getId.set(category,1);
        category.setCategory_name("test_category");
        var toUpdate = new Category();
        toUpdate.setCategory_name("foobar");

        assertThat(category.getId_category()).isEqualTo(1);
        when(repo.existsById(1)).thenReturn(true);
        when(repo.findById(1)).thenReturn(Optional.of(category));
        when(repo.save(category)).thenReturn(category);
        assertDoesNotThrow(() -> new CategoryService(repo).updateCategory(1,toUpdate));
    }

}