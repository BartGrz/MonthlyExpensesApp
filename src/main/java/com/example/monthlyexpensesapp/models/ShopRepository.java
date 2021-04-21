package com.example.monthlyexpensesapp.models;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository {

    List<Shop> findAll();
    Shop save(Shop entity);
    Optional<Shop> findById(Integer integer);
    void delete(Shop shop);


}
