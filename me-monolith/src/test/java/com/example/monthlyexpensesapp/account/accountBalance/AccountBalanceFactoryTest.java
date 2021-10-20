package com.example.monthlyexpensesapp.account.accountBalance;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.rest.core.annotation.Description;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountBalanceFactoryTest {

    @Test
    @Description("Check if Factory creates new AccountBalance object without errors")
    void checkIfAccountBalanceObjectWillBeCreatedFromAccountBalanceDto() throws IllegalAccessException, NoSuchFieldException {

        //given
        var accountBalanceDto = mock(AccountBalanceDto.class);
        var account = new Account();
        Field id = account.getClass().getDeclaredField("id_account");
        id.setAccessible(true);
        id.set(account,1);
        account.setAccount_name("foo");
        var deserialize = AccountBalanceDto.deserialize(1,20,account);

        //then
        when(accountBalanceDto.getBalance()).thenReturn(deserialize.getBalance());
        when(accountBalanceDto.getId_account()).thenReturn(deserialize.getId_account());
        when(accountBalanceDto.getAccount()).thenReturn(account);

        //under test
        assertDoesNotThrow(()-> {
            var refactored = AccountBalanceFactory.from(accountBalanceDto);
            System.out.println(refactored);
        });



    }
}