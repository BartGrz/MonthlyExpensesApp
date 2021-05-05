package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.ProductsGroup;
import com.example.monthlyexpensesapp.models.ProductsGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlProductsGroupRepository extends ProductsGroupRepository, JpaRepository<ProductsGroup, Integer> {


    List<ProductsGroup> findAll();


    ProductsGroup save(ProductsGroup productsGroup);


    Optional<ProductsGroup> findById(Integer integer);

    boolean existsById(Integer integer);
}
