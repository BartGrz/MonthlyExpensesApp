package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Shop;
import com.example.monthlyexpensesapp.adapter.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShopController {
    public static final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/shop")
     ResponseEntity<List<Shop>> findAll() {
            logger.warn("exposing data");

        return ResponseEntity.ok(shopRepository.findAll());
    }

    @GetMapping("/shop/{id}")
    ResponseEntity<Shop> findById(@PathVariable int id) {

        return shopRepository.findById(id).map(shop -> ResponseEntity.ok(shop))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/shop")
    ResponseEntity<Shop> createShop(@RequestBody Shop entity) {

        Shop res = shopRepository.save(entity);

        return ResponseEntity.created(URI.create("/" +res.getId_shop())).body(res);
    }

    @PutMapping("/shop/{id}")
    ResponseEntity<Shop> updateShop(@PathVariable int id, @RequestBody Shop toUpdate){

        if(shopRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
            shopRepository.findById(id).ifPresent(shop -> {
                shop.updateFrom(toUpdate);
                shopRepository.save(shop);
        });
       return ResponseEntity.noContent().build();


    }
    @DeleteMapping("/shop/{id}")
    ResponseEntity<Shop> deleteShop(@PathVariable int id ){

        if(shopRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Shop res = shopRepository.findById(id).get();
        shopRepository.delete(res);
        logger.warn("shop with id=" + id +  " deleted ");
        return ResponseEntity.noContent().build();


    }
}
