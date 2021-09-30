package com.example.monthlyexpensesapp.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class ShopController {
    public static final Logger logger = LoggerFactory.getLogger(ShopController.class);
    private ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping(value = "/show-shops")
     String getAll(Model model) {
        model.addAttribute("shops",getShops());
        return "showAllShops";
    }
    @GetMapping(value = "/edit-shop")
    String editShop(Model model) {
        model.addAttribute("shops",getShops());
        return "editShop";
    }
    @GetMapping(value = "/delete-shop")
    String deleteShop(Model model) {
       model.addAttribute("shops",getShops());
        return "deleteShop";
    }
    @GetMapping(value = "/add-shop")
    String addingNewShop(Model model) {
        return "addShop";
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
    private List<Shop> getShops() {
        return shopRepository.findAll();
    }
}
