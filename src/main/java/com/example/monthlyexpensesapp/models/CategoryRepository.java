package com.example.monthlyexpensesapp.models;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAll();
    Optional<Category> findById(Integer integer);
    Category save (Category category);
    void delete (Category category);
    boolean existsById(Integer integer);




}
