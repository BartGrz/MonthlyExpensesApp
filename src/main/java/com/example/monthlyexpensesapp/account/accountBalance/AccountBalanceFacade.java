package com.example.monthlyexpensesapp.account.accountBalance;


import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceFacade {

    private final AccountBalanceRepository accountBalanceRepository ;
    private static final Logger logger = LoggerFactory.getLogger(AccountDebtFacade.class);

    AccountBalanceFacade(final AccountBalanceRepository accountBalanceRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
    }

    public void updateBalanceForAccount(Account account,double total_balance){
        var toCreate = AccountBalanceDto.deserialize(account.getId_account(),total_balance,account);
        accountBalanceRepository.save(AccountBalanceFactory.from(toCreate));
        logger.info("account balance with id:" + account.getId_account() + " has been updated");
    }
}
