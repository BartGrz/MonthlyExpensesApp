package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.logic.BillWriteModel;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.services.BillService;
import com.example.monthlyexpensesapp.services.ProductService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private BillService billService;

    @Autowired
    public void setBillService(BillService billService) {
        this.billService = billService;
    }

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
    ResponseEntity<List<Product>> readAllProducts(Model model)  {
        var products = productRepository.findAll();
        
        if (productRepository.findAll().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        model.addAttribute("productsPage", products);

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
    ResponseEntity<Product> createProduct
            (@RequestBody Product toCreate, @RequestParam int id_category, @RequestParam int id_shop, @RequestParam int id_account) {

        BillWriteModel billWriteModel = new BillWriteModel(productRepository, categoryRepository, accountRepository, billRepository, shopRepository);
        var product = billWriteModel.addProductAndCreateBill(toCreate, id_category, id_shop, id_account, LocalDate.of(2021, 7, 7));
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(toCreate);
    }
    
    @PostMapping("/add_to_bill")
    ResponseEntity<Product> createProductAndAddTobill(@RequestBody Product toCreate, @RequestParam int id_category, @RequestParam int id_bill) {
        
        ProductService productService = new ProductService(productRepository, billRepository, categoryRepository);
        
        var product = productService.addProductToExistingBill(toCreate, id_category, id_bill);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(toCreate);
    }
    
    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@RequestBody Product toCreate, @PathVariable("id") int id_product) {
        
        if (!productRepository.existsById(id_product)) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(id_product).get();
        product.updateFrom(toCreate);
        var updated = productRepository.save(product);
        
        return ResponseEntity.created(URI.create("/" + product.getId_product())).body(updated);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProduct (@PathVariable("id") int id) {
    ProductService productService = new ProductService(productRepository,billRepository,categoryRepository);
    var product = productService.deleteProduct(id);
    if(product==null) {
        return ResponseEntity.notFound().build();
    }
        return ResponseEntity.ok(product);
    }
}
