package com.example.monthlyexpensesapp.account;

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
      /*  AccountController accountController = getAccountController();
        accountController.updateAccount(mock(Account.class),anyInt());*/

    }
    @Test
    void deleteAccount () {

        AccountController accountController = getAccountController();
        accountController.deleteAccount(anyInt());

    }

    private AccountController getAccountController() {
        var accountRepository = mock(AccountRepository.class);
        var accountService = mock(AccountService.class);
        var accountQueryRepository = mock(AccountQueryRepository.class);
        return new AccountController(accountRepository, accountService, accountQueryRepository);
    }
}