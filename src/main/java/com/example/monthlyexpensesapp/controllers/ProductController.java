package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.logic.BillWriteModel;
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

    private ProductService productService;

    public ProductController(ProductService productService ) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<List<Product>> readAllProducts() {
        var products = productService.readAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> readProductById(@PathVariable int id) {

        var product = productService.readProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add-to-bill")
    ResponseEntity<Product> createProductAndAddTobill(@RequestBody Product toCreate, @RequestParam int id_category, @RequestParam int id_bill, @RequestParam int id_account) {

        var product = productService.addProductToExistingBill(toCreate, id_category, id_bill, id_account);
        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(toCreate);
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@RequestBody Product toCreate, @PathVariable("id") int id_product) {

        var product = productService.readProductById(id_product);
        product.updateFrom(toCreate);
        var updated = productService.updateProduct(product);
        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(updated);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
