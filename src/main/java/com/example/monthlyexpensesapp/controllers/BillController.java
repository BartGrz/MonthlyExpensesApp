package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.adapter.ShopRepository;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.services.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bills")
@Transactional
public class BillController {
    
    private ShopRepository shopRepository;
    private BillRepository billRepository;
    private AccountRepository accountRepository;
    private ProductRepository productRepository;
    
    public BillController(ShopRepository shopRepository, BillRepository billRepository, AccountRepository accountRepository, ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.billRepository = billRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }
    
    @GetMapping("/")
    ResponseEntity<List<Bill>> readAllBills () {
        var list = billRepository.findAll();
        if(list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }
    @PostMapping("/")
    ResponseEntity<Bill> openBill(@RequestBody Bill toCreate,@RequestParam int id_shop, @RequestParam int id_account) {
        var billservice = new BillService(billRepository,shopRepository,accountRepository, productRepository);
        var bill = billservice.openbill(toCreate,id_shop,id_account);
        if(bill==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/"+bill.getId_bill())).body(toCreate);
        
    }
    @DeleteMapping("/{id}")
    ResponseEntity<Bill> deleteBill(@PathVariable("id") int id_bill) {
        var billService = new BillService(billRepository,shopRepository,accountRepository,productRepository);
        var bill = billService.deleteBill(id_bill);
        if(bill!=null) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
