package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.services.AccountService;
import com.example.monthlyexpensesapp.services.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    private BillService billService;
    private AccountService accountService;

    public BillController(BillService billService, AccountService accountService) {
        this.billService = billService;
        this.accountService = accountService;
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
        var bill = billService.openbill(id_shop, id_account,toCreate.getGroup_date());
        if (bill == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/" + bill.getId_bill())).body(bill);

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
        var bill = billService.getBill(id);
        billService.toogleBill(id);
        accountService.updateDebtOfAccounts(bill);
        return ResponseEntity.noContent().build();

    }


}
