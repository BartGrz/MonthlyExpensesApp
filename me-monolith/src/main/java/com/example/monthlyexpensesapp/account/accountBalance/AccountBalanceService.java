package com.example.monthlyexpensesapp.account.accountBalance;

import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AccountBalanceService {

    private AccountBalanceQueryRepository accountBalanceQueryRepository;
    private AccountBalanceRepository accountBalanceRepository;


    AccountBalanceService(final AccountBalanceQueryRepository accountBalanceQueryRepository, final AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceQueryRepository = accountBalanceQueryRepository;
        this.accountBalanceRepository = accountBalanceRepository;
    }



}
