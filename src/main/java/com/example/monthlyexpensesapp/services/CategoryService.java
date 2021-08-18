package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final static Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public Category addNewCategory(String name) {
        if(!categoryRepository.existByName(name)) {
            var category = new Category();
            category.setCategory_name(name);
            var saved = categoryRepository.save(category);
            logger.info("category with name = " + name + " added ");
            return saved;
        }else{
            throw new IllegalStateException("category already exist");
        }
    }
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    public void deleteCategory(int id) {
        if(!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("category with given id not found");
        }
            var category = categoryRepository.findById(id).get();
            logger.warn("category with name = " + category.getCategory_name() + " deleted");
            categoryRepository.delete(category);
           
        
    }
}
