package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFactory;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtRepository;
import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;
import com.example.monthlyexpensesapp.bill.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountDebtFacade {
public static final Logger logger = LoggerFactory.getLogger(AccountDebtFacade.class);
    private final AccountDebtRepository accountDebtRepository;

    public AccountDebtFacade(final AccountDebtRepository accountDebtRepository) {
        this.accountDebtRepository = accountDebtRepository;
    }

    /**
     * creates new instance of AccountDebt class and saving it using jpa repository
     * @param account
     * @param bill
     * @param debt
     */
    public void saveNewDebt(Account account,Bill bill,double debt){
        var accountDebt = AccountDebtDto.deserialize(account.getId_account(), bill.getAccount().getId_account(),debt,account);
        accountDebtRepository.save(AccountDebtFactory.from(accountDebt));
        logger.info("adding new debt , account : " + account.getAccount_name() + " owes  " + debt + " to " + bill.getAccount().getAccount_name());
    }
    /**
     * creates new instance of AccountDebt class and saving it (updating) using jpa repository
     * @param account
     * @param bill
     * @param total
     */
    public void updateDebt(Account account, Bill bill,double total){
        var accountDebt = AccountDebtDto.deserialize(account.getId_account(), bill.getAccount().getId_account(),total,account);
        //accountDebtRepository.save(AccountDebtFactory.from(accountDebt));
        accountDebtRepository.updateDebtById_account(total,account.getId_account());
        logger.warn(  account.getAccount_name()  + "  owe " + total+ " in total to " + bill.getAccount().getAccount_name());
    }
}
