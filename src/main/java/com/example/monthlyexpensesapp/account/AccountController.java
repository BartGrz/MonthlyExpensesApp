package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.account.dto.AccountDto;
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
   private AccountQueryRepository accountQueryRepository;

    public AccountController(AccountRepository accountRepository, AccountService accountService, final AccountQueryRepository accountQueryRepository) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.accountQueryRepository = accountQueryRepository;
    }

    @GetMapping("/account")
    ResponseEntity<List<AccountDto>> readAll() {

        return ResponseEntity.ok(accountQueryRepository.findAllDtoBy());
    }

    @GetMapping("/account/{id}")
    ResponseEntity<AccountDto> readById(@PathVariable int id) {

        return accountQueryRepository.findDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/account")
    ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto toCreate) {

        var toBeCreated = AccountFactory.from(toCreate);
        Account result = accountService.creatingAccount(toBeCreated);
        return ResponseEntity.created(URI.create("/" + result.getId_account())).body(toCreate);
    }

    @PutMapping("/account/{id}")
    ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto toUpdate, @PathVariable int id) {

        var toBeUpdated = AccountFactory.from(toUpdate);
        accountService.updateFrom(id,toBeUpdated);

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
