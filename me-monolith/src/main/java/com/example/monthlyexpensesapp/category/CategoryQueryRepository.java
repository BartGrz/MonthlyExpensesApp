package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.category.dto.CategoryDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryRepository extends Repository<Category,Integer> {

List<CategoryDto> findAllDtoBy();

/*cannot force spring to create query base on projection,
 substitute method with @query anno */
@Query("select c from Category c where c.id_category=:id")
Optional<CategoryDto> findById(@Param("id") int id_category);
List<Category> findAll();
}
