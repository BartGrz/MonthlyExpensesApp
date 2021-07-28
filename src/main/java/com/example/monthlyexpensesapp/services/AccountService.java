package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account creatingAccount(Account account) {
        if (accountRepository.existsById(account.getId_account())) {
            throw new IllegalStateException("Account already exist");
        }
        var accountCreated = accountRepository.save(account);
        accountRepository.saveToAccountDebt(accountCreated.getId_account());
        logger.info("Account " + accountCreated.getAccount_name() + " created");
        return accountCreated;
    }

    /**
     * logic : must updating debt based on how many products has false in 'common' row,
     * it will calculate how many money is owed to the bill payer
     */

    public void updateAccount(int id_account) {

        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account with given id : " + id_account + " does not exist");
        }
        List<Account> accountList = accountRepository.findAll();
        var account = accountRepository.findById(id_account).get();
        accountList.removeIf(acc -> acc.equals(account));


        accountList.forEach(acc -> acc.getBills().stream()
                .map(Bill::getProducts)
                .forEach(products -> {
                    var res = products.stream()
                            .filter(product -> product.getAccount().equals(acc))
                            .mapToDouble(Product::getProduct_price).sum();
                    accountRepository.updateAccountDebt(res, id_account);
                    logger.warn("result = " + res);
                    logger.info("account debt with id:" + id_account + " has been updated");
                }));

        account.getBills().stream()
                .map(Bill::getProducts)
                .forEach(products -> {
                    var res = products.stream()
                            .filter(product -> product.getAccount().getId_account() == id_account)
                            .mapToDouble(Product::getProduct_price).sum();
                    accountRepository.updateAccountDebt(res, id_account);
                    logger.warn("result = " + res);
                    logger.info("account balance with id:" + id_account + " has been updated");
                });

    }

}
