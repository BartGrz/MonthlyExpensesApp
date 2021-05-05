package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.models.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlProductRepository extends ProductRepository, JpaRepository<Product,Integer> {



    List<Product> findAll();
    Product save(Product entity);
    Optional<Product> findById(Integer integer);
    boolean existsById(Integer integer);
    void delete(Product product);
}
