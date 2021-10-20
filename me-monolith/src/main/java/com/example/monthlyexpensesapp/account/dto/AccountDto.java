package com.example.monthlyexpensesapp.account.dto;

import com.example.monthlyexpensesapp.bill.Bill;
import com.example.monthlyexpensesapp.bill.dto.BillDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Set;

@JsonDeserialize(as = AccountDto.DeserializeImp.class)
public interface AccountDto {
    static DeserializeImp deserialize(int id, String name, Set<Bill> bills){
        return new DeserializeImp(id,name,bills);
    }

    int getId_account();
    String getAccount_name();
    Set<Bill> getBills();

    class DeserializeImp implements AccountDto {
        private int id_account;
        private String account_name;
        private Set<Bill> bills;

        DeserializeImp(final int id_account, final String account_name, final Set<Bill> bills) {
            this.id_account = id_account;
            this.account_name = account_name;
            this.bills = bills;
        }

        @Override
        public int getId_account() {
            return id_account;
        }

        @Override
        public String getAccount_name() {
            return account_name;
        }

        @Override
        public Set<Bill> getBills() {
            return bills;
        }
    }

}
