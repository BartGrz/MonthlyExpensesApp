package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findAll();
    Optional<Category> findById(Integer integer);
    Category save (Category category);
    void delete (Category category);
    @Override
    boolean existsById(Integer integer);
}
