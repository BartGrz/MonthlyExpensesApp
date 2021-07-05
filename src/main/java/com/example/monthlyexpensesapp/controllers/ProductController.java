package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductRepository productRepository;
    private BillRepository billRepository;
    private CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository,
                             BillRepository billRepository, CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.categoryRepository = categoryRepository;
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

    @PostMapping("/{id_category}")
    ResponseEntity<Product> createProduct(@RequestBody Product toCreate, @PathVariable("id_category")int id_category) {
        ProductService productService = new ProductService(productRepository,billRepository,categoryRepository);

        var product = productService.addProduct(toCreate,id_category);


        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(toCreate);
    }


}
