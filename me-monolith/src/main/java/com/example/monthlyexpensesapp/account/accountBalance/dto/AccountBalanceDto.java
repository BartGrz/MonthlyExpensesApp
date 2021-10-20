package com.example.monthlyexpensesapp.account.accountBalance.dto;

import com.example.monthlyexpensesapp.account.Account;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = AccountBalanceDto.DeserializeImp.class)
public interface AccountBalanceDto {

    static DeserializeImp deserialize (int id ,double balance, Account account) {
        return new DeserializeImp(id,balance,account);
    }

    int getId_account();
    double getBalance();
    Account getAccount();

    class DeserializeImp implements AccountBalanceDto {

        private int id_account;
        private double balance;
        private Account account;

        DeserializeImp(final int id_account, final double balance,Account account) {
            this.id_account = id_account;
            this.balance = balance;
            this.account=account;
        }

        @Override
        public int getId_account() {
            return id_account;
        }

        @Override
        public double getBalance() {
            return balance;
        }

        @Override
        public Account getAccount() {
            return account;
        }
    }

}
