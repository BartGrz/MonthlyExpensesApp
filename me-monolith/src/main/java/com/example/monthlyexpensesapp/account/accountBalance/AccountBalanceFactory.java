package com.example.monthlyexpensesapp.account.accountBalance;

import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;

public class AccountBalanceFactory {

    public static AccountBalance from (AccountBalanceDto accountBalanceDto) {
        var accountBalance = new AccountBalance();
        var pk = new AccountBalancePK();
        pk.setId_account(accountBalanceDto.getId_account());
        accountBalance.setBalance(accountBalanceDto.getBalance());
        accountBalance.setId(pk);
        accountBalance.setAccount(accountBalanceDto.getAccount());
        return accountBalance;

    }

}
