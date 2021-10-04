package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.account.dto.AccountDto;

class AccountFactory {

   static Account from(AccountDto source) {
       var account = new Account();
       account.setAccount_name(source.getAccount_name());
       account.setBills(source.getBills());
       return account;
   }

}
