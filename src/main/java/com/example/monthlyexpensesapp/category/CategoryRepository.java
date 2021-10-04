package com.example.monthlyexpensesapp.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Integer> {

/*    List<Category> findAll();
    Optional<Category> findById(Integer integer);*/
    Category save (Category category);
    void delete (Category category);
    @Override
    boolean existsById(Integer integer);
    @Query("select c from Category c where c.category_name=:name")
    Optional<Category> findByName(@Param("name") String name);
    @Query(nativeQuery = true , value = "select count(*) >0 from Category c where c.category_name=:name")
    boolean existByName(@Param("name")String name);
}
