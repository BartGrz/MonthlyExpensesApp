package com.example.monthlyexpensesapp.shop;

import com.example.monthlyexpensesapp.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ShopRepository extends JpaRepository<Shop,Integer> {

    List<Shop> findAll();
    Shop save(Shop entity);
    Optional<Shop> findById(Integer integer);
    @Override
    void delete(Shop shop);
    @Query("select s from Shop s where s.shop_name=:name")
    Optional<Shop> findByName(@Param("name") String name);
}
