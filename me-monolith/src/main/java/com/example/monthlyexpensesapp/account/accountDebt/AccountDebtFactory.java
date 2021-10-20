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
        accountDebt.setId(pk);
        accountDebt.setDebt(source.getDebt());
        accountDebt.setAccount(source.getAccount());
        accountDebt.setId_account_owed(source.getIdAccount_owed());
        return accountDebt;

    }

}
