package com.example.monthlyexpensesapp.models;



import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();
    Product save(Product entity);
    Optional<Product> findById(Integer integer);
    boolean existsById(Integer integer);
    void delete(Product product);

}
