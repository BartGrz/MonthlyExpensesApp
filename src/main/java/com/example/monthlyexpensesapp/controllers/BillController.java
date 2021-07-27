package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.adapter.ShopRepository;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.services.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/")
    ResponseEntity<List<Bill>> readAllBills() {
        var list = billService.getAllBills();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    ResponseEntity<List<Product>> readAllProductsFromBill(@PathVariable int id) {
        var result = billService.getAllProducts(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    ResponseEntity<Bill> openBill(@RequestBody Bill toCreate, @RequestParam int id_shop, @RequestParam int id_account) {
        var bill = billService.openbill(toCreate, id_shop, id_account);
        if (bill == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/" + bill.getId_bill())).body(toCreate);

    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProductFromBill(@RequestBody Product product, @PathVariable int id) {

        var updated = billService.updateProduct(id, product);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Bill> deleteBill(@PathVariable("id") int id_bill) {

        var bill = billService.deleteBill(id_bill);
        if (bill != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/{id}")
    @Transactional
    ResponseEntity<?> toogleBill(@PathVariable("id") int id) {
        billService.toogleBill(id);
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
