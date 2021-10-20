package com.example.monthlyexpensesapp.category;

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

    public Category addNewCategory(Category toCreate) {
        if (!categoryRepository.existByName(toCreate.getCategory_name())) {

            var saved = categoryRepository.save(toCreate);
            logger.info("category with name = " + saved.getCategory_name() + " added ");
            return saved;
        } else {
            throw new IllegalStateException("category already exist");
        }
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("category with given id not found");
        }
        var category = categoryRepository.findById(id).get();
        logger.warn("category with name = " + category.getCategory_name() + " deleted");
        categoryRepository.delete(category);
    }
    public Category updateCategory(int id, Category toUpdate) {
        if(!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("category with given id not found");
        }
        var category = categoryRepository.findById(id).get();
        category.updateFrom(toUpdate);
        categoryRepository.save(category);
        logger.info("category name with id=" + id+ " has been updated to " + category.getCategory_name());
        return category;
    }
}
