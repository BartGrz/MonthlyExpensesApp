package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.dto.CategoryDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryRepository extends Repository<Category,Integer> {

List<CategoryDto> findAllDtoBy();
Optional<CategoryDto> findById(int id_category);
List<Category> findAll();
}
