package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Test
    void creatingAccountWithAddingItToAccountDebtTable() throws NoSuchFieldException, IllegalAccessException {
        var accountRepo = mock(AccountRepository.class);
        Account account = new Account();
        Field getId = account.getClass().getDeclaredField("id_account");
        getId.setAccessible(true);
        getId.set(account, 1);
        account.setAccount_name("Common");

        when(accountRepo.existsById(1)).thenReturn(false);
        when(accountRepo.save(account)).thenReturn(account);
        assertThat(account.getId_account()).isEqualTo(1);

        AccountService accountService = new AccountService(accountRepo);
        assertDoesNotThrow(() -> accountService.creatingAccount(account));
    }

    @Test
    void creatingAccountWithAddingItToAccountDebtTable_throwingIllegalStateIfAccountExist() throws NoSuchFieldException, IllegalAccessException {
        var accountRepo = mock(AccountRepository.class);
        Account account = new Account();
        Field getId = account.getClass().getDeclaredField("id_account");
        getId.setAccessible(true);
        getId.set(account, 1);
        account.setAccount_name("Common");

        assertThat(account.getId_account()).isEqualTo(1);
        when(accountRepo.existsById(1)).thenReturn(true);
        when(accountRepo.save(account)).thenReturn(account);

        AccountService accountService = new AccountService(accountRepo);
       assertThrows(IllegalStateException.class,
                () -> accountService.creatingAccount(account));

    }


    @Test
    void checkIfUpdatingAccountsDebtAndBalance_working() throws NoSuchFieldException, IllegalAccessException {
        //given
        var accountRepo = mock(AccountRepository.class);
        Account account = new Account();
        Account account_2 = new Account();
        Bill bill = new Bill();
        Product prod = new Product();
        Product prod_2 = new Product();
        Product prod_3 = new Product();
        Set<Bill> bills = new HashSet<>();
        Set<Product> productSet = new HashSet<>();
        List<Account> accountList = new ArrayList<>();

        //initializing account id
        Field getId = account.getClass().getDeclaredField("id_account");
        getId.setAccessible(true);
        getId.set(account, 1);

        //initializing account_2 id
        Field getId_2 = account_2.getClass().getDeclaredField("id_account");
        getId_2.setAccessible(true);
        getId_2.set(account_2, 2);

        //adding producrs to list
        prod.setProduct_price(20);
        prod.setAccount(account);
        prod_2.setProduct_price(40);
        prod_2.setAccount(account_2);
        prod_3.setProduct_price(40);
        prod_3.setAccount(account);

        //adding account to bill
        bill.setAccount(account_2);

        //adding products to set
        productSet.add(prod);
        productSet.add(prod_2);
        productSet.add(prod_3);

        bill.setProducts(productSet); //setting product set for bill
        bills.add(bill); // adding bill to set
        account_2.setBills(bills); // adding bill set to account
        account.setBills(bills); // adding bill set to account

        accountList.add(account); //adding account to list
        accountList.add(account_2); //adding account to list

        //then
        when(accountRepo.findAll()).thenReturn(accountList);
        when(accountRepo.existsById(1)).thenReturn(true);
        when(accountRepo.findById(1)).thenReturn(java.util.Optional.of(account));
        assertThat(account_2.getBills()).isEqualTo(bills);
        assertThat(account.getBills()).isEqualTo(bills);
        assertThat(account_2.getId_account()).isEqualTo(2);
        assertThat(bill.getProducts()).isEqualTo(productSet);
        assertThat(account.getId_account()).isEqualTo(1);
        assertThat(account_2.getId_account()).isEqualTo(2);

        //udner test
        AccountService service = new AccountService(accountRepo);
        assertDoesNotThrow(() -> service.updateAccount(1));


    }
}
