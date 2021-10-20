package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.accountBalance.AccountBalanceFactory;
import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountDebtFactoryTest {


    @Test
    void checkIfFactoryIsWorking_allFieldsAreFilled() throws NoSuchFieldException, IllegalAccessException {
        //given
        var accountDebtDto = mock(AccountDebtDto.class);
        var account = new Account();
        Field id = account.getClass().getDeclaredField("id_account");
        id.setAccessible(true);
        id.set(account,1);
        account.setAccount_name("foo");
        var deserialize = AccountDebtDto.deserialize(account.getId_account(),2,25.0,account);

        //then
        when(accountDebtDto.getDebt()).thenReturn(deserialize.getDebt());
        when(accountDebtDto.getId_account()).thenReturn(deserialize.getId_account());
        when(accountDebtDto.getIdAccount_owed()).thenReturn(deserialize.getIdAccount_owed());
        when(accountDebtDto.getAccount()).thenReturn(account);

        //under test
        assertDoesNotThrow(()-> {
            var refactored = AccountDebtFactory.from(accountDebtDto);
            System.out.println(refactored);
        });


    }

}