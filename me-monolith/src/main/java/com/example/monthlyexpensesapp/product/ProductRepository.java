package com.example.monthlyexpensesapp.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Integer> {



    List<Product> findAll();
    Product save(Product entity);
    Optional<Product> findById(Integer integer);
    boolean existsById(Integer integer);
    void delete(Product product);
    @Modifying
    @Transactional
    @Query(value = "delete from Product p where p.id_product=:id")
    void deleteById (@Param("id") Integer id);
}
