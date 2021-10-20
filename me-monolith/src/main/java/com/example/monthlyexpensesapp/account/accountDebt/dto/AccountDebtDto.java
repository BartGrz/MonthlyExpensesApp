package com.example.monthlyexpensesapp.account.accountDebt.dto;

import com.example.monthlyexpensesapp.account.Account;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = AccountDebtDto.DeserializeImp.class)
public interface AccountDebtDto {

    static DeserializeImp deserialize(int id_account,int id_account_owed,double debt, Account account){
        return new DeserializeImp(id_account,id_account_owed,debt,account);
    }

    int getId_account();
    int getIdAccount_owed();
    double getDebt();
    Account getAccount();


    class DeserializeImp implements AccountDebtDto {

        private int id_account;
        private int id_account_owed;
        private double debt;
        private Account account;

        DeserializeImp(final int id_account, final int id_account_owed, final double debt, final Account account) {
            this.id_account = id_account;
            this.id_account_owed = id_account_owed;
            this.debt = debt;
            this.account = account;
        }

        @Override
        public int getId_account() {
            return id_account;
        }

        @Override
        public int getIdAccount_owed() {
            return id_account_owed;
        }

        @Override
        public double getDebt() {
            return debt;
        }

        @Override
        public Account getAccount() {
            return account;
        }
    }
}
