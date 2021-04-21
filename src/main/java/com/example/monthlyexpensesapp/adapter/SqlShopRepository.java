package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Shop;
import com.example.monthlyexpensesapp.models.ShopRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlShopRepository extends ShopRepository, JpaRepository<Shop,Integer> {

    List<Shop> findAll();
    Shop save(Shop entity);
    Optional<Shop> findById(Integer integer);
    @Override
    void delete(Shop shop);
}
