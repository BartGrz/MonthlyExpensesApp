package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.controllers.IllegalExceptionProcessing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@IllegalExceptionProcessing
@RestController
public class AccountController {

   private AccountRepository accountRepository;
   private AccountService accountService;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping("/account")
    ResponseEntity<List<Account>> readAll() {

        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping("/account/{id}")
    ResponseEntity<Account> readByID(@PathVariable int id) {

        return accountRepository.findById(id).map(account ->
                ResponseEntity.ok(account))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/account")
    ResponseEntity<Account> createAccount(@RequestBody Account toCreate) {

        Account result = accountService.creatingAccount(toCreate);

        return ResponseEntity.created(URI.create("/" + result.getId_account())).body(toCreate);
    }

    @PutMapping("/account/{id}")
    ResponseEntity<Account> updateAccount(@RequestBody Account toUpdate, @PathVariable int id) {

        if (!accountRepository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        accountRepository.findById(id).ifPresent(account -> {
            account.updateFrom(toUpdate);
            accountRepository.save(account);
        });

        return ResponseEntity.noContent().build();
    }

    /**
     * block possibility of deleting account if it is linked with any bills (which may indicate owning other account money)
     *
     * @param id
     * @return
     */
    @DeleteMapping("/account/{id}")
    ResponseEntity<Account> deleteAccount (@PathVariable int id) {

        if(!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Account account = accountRepository.findById(id).get();
        accountRepository.delete(account);

        return ResponseEntity.noContent().build();
    }
}
