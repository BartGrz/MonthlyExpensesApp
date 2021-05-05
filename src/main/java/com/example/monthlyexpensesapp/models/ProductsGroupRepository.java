package com.example.monthlyexpensesapp.models;


import java.util.List;
import java.util.Optional;

public interface ProductsGroupRepository {

    List<ProductsGroup> findAll();

    ProductsGroup save(ProductsGroup productsGroup);

    Optional<ProductsGroup> findById(Integer integer);

    boolean existsById(Integer integer);

}
