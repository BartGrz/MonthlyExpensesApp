package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.logic.BillWriteModel;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductRepository productRepository;
    private BillRepository billRepository;
    private CategoryRepository categoryRepository;
    private ShopRepository shopRepository;
    private AccountRepository accountRepository;

    public ProductController(ProductRepository productRepository,
                             BillRepository billRepository, CategoryRepository categoryRepository,
                             ShopRepository shopRepository, AccountRepository accountRepository) {
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.categoryRepository = categoryRepository;
        this.shopRepository = shopRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    ResponseEntity<List<Product>> readAllProducts() {
        var products = productRepository.findAll();

        if (productRepository.findAll().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> readProductById(@PathVariable int id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(id).get();
        return ResponseEntity.ok(product);
    }

    @PostMapping("/")
    ResponseEntity<Product> createProduct(@RequestBody Product toCreate,@RequestParam int id_category, @RequestParam int id_shop, @RequestParam int id_account) {


        BillWriteModel billWriteModel = new BillWriteModel(productRepository,categoryRepository,accountRepository,billRepository,shopRepository);

        var product = billWriteModel.addProductAndCreateBill(toCreate,id_category,id_shop,id_account, LocalDate.of(2021,7,7));
        if(product==null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(toCreate);
    }


}
