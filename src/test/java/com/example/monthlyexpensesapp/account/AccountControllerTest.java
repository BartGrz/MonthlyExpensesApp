package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.account.AccountController;
import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.account.AccountService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;


class AccountControllerTest {

    @Test
    void readAll() {

        AccountController accountController = getAccountController();
        accountController.readAll();
    }

    @Test
    void readByID() {

        AccountController accountController = getAccountController();
        accountController.readByID(anyInt());
    }
    @Test
    void createAccount () {

        AccountRepository accountRepository = mock(AccountRepository.class);
        accountRepository.save(mock(Account.class));

    }
    @Test
    void updateAccount() {
        AccountController accountController = getAccountController();
        accountController.updateAccount(mock(Account.class),anyInt());

    }
    @Test
    void deleteAccount () {

        AccountController accountController = getAccountController();
        accountController.deleteAccount(anyInt());

    }

    private AccountController getAccountController() {
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountService accountService = mock(AccountService.class);
        return new AccountController(accountRepository, accountService);
    }
}