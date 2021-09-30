package com.example.monthlyexpensesapp.bill;

import com.example.monthlyexpensesapp.bill.dto.BillDto;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
class BillController {

    private BillService billService;
    private AccountService accountService;
    private final BillQueryRepository billQueryRepository;

    public BillController(BillService billService, AccountService accountService, final BillQueryRepository billQueryRepository) {
        this.billService = billService;
        this.accountService = accountService;
        this.billQueryRepository = billQueryRepository;
    }

    @GetMapping("/")
    ResponseEntity<List<BillDto>> readAllBills() {
      /*  var list = billService.getAllBills();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);*/
        var results = billQueryRepository.findAllBy();
        return ResponseEntity.ok(results);

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
    ResponseEntity<Bill> openBill(@RequestBody BillDto toCreate, @RequestParam int id_shop, @RequestParam int id_account) {
        var bill = billService.openbill(id_shop, id_account, LocalDate.parse(toCreate.getBill_date()));
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
    ResponseEntity<?> toggleBill(@PathVariable("id") int id) {
        var bill = billService.getBill(id);
        billService.toggleBill(id);
        accountService.updateDebtOfAccounts(bill);
        return ResponseEntity.noContent().build();

    }


}
