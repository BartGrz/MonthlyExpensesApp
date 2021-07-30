package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

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
    @Test
    void checkIfAutoCalcultaingOfAccountsDebt_working () throws NoSuchFieldException, IllegalAccessException {
        var accountRepository  = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        Account account_1 = new Account();
        Account account_2 = new Account();
        Account account_3 = new Account();
        Product product_1 = new Product();
        Product product_2 = new Product();
        Product product_3 = new Product();
        Bill bill = new Bill();
        Set<Product> products = new HashSet<>();
        List<Account> accountList = new ArrayList<>();
        Set<Bill> bills = new HashSet<>();

        Field account_id_1 = account_1.getClass().getDeclaredField("id_account");
        account_id_1.setAccessible(true);
        account_id_1.set(account_1,1);
        Field account_id_2 = account_2.getClass().getDeclaredField("id_account");
        account_id_2.setAccessible(true);
        account_id_2.set(account_2,2);
        Field account_id_3 = account_3.getClass().getDeclaredField("id_account");
        account_id_3.setAccessible(true);
        account_id_3.set(account_3,3);

        Field product_id = product_1.getClass().getDeclaredField("id_product");
        product_id.setAccessible(true);
        product_id.set(product_1,1);
        product_1.setAccount(account_1);

        Field product_id_2 = product_2.getClass().getDeclaredField("id_product");
        product_id_2.setAccessible(true);
        product_id_2.set(product_2,2);
        product_2.setAccount(account_2);

        Field product_id_3 = product_3.getClass().getDeclaredField("id_product");
        product_id_3.setAccessible(true);
        product_id_3.set(product_3,3);
        product_3.setAccount(account_2);

        products.add(product_1);
        products.add(product_2);
        products.add(product_3);

        accountList.add(account_1);
        accountList.add(account_2);
        accountList.add(account_3);


        final double [] add = {0};
        products.stream().forEach(product -> {
            product.setProduct_price(20+ add[0]);
            add[0]=product.getProduct_price();
        });

        bill.setProducts(products);
        bills.add(bill);
        account_1.setBills(bills);
        bill.setAccount(account_1);

        when(accountRepository.findAll()).thenReturn(accountList);
        assertThat(account_1.getBills()).isEqualTo(bills);
        assertThat(bill.getProducts()).isEqualTo(products);
        assertThat(bill.getAccount()).isEqualTo(account_1);
        assertThat(product_1.getAccount()).isEqualTo(account_1);
        assertThat(product_2.getAccount()).isEqualTo(account_2);
        assertThat(product_3.getAccount()).isEqualTo(account_2);
        accountService.updateDebtOfAccounts(bill);


    }
}
