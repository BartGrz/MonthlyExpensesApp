package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;

public class AccountDebtFactory {

    /**
     * creates new instance of AccountDebt class based on Dto interface
     * @param source
     * @return
     */
   public static AccountDebt from(AccountDebtDto source) {

        var accountDebt = new AccountDebt();
        var pk = new AccountDebtPK();
        pk.setId_account(source.getId_account());
        pk.setId_account_owed(source.getIdAccount_owed());
        accountDebt.setId(pk);
        accountDebt.setDebt(source.getDebt());
        accountDebt.setAccount(source.getAccount());
        return accountDebt;

    }

}
