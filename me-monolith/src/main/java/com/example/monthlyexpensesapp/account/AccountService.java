package com.example.monthlyexpensesapp.account;


import com.example.monthlyexpensesapp.account.accountBalance.AccountBalanceFacade;
import com.example.monthlyexpensesapp.account.accountBalance.AccountBalanceFactory;
import com.example.monthlyexpensesapp.account.accountBalance.AccountBalanceRepository;
import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFacade;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFactory;
import com.example.monthlyexpensesapp.account.accountDebt.AccountDebtRepository;
import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;
import com.example.monthlyexpensesapp.bill.Bill;
import com.example.monthlyexpensesapp.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private AccountRepository accountRepository;
    private AccountBalanceRepository accountBalanceRepository;
    private final AccountDebtFacade accountDebtFacade;
    private final AccountBalanceFacade updateBalanceForAccount;

    public AccountService(AccountRepository accountRepository, final AccountDebtFacade accountDebtFacade, AccountBalanceFacade updateBalanceForAccount) {
        this.accountRepository = accountRepository;
        this.accountDebtFacade = accountDebtFacade;
        this.updateBalanceForAccount = updateBalanceForAccount;
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByName(String name) {

        var account = accountRepository.findByName(name).get();
        if (Optional.of(account).isEmpty()) {
            throw new IllegalArgumentException("category with given name does not exist");
        }
        return Optional.of(account);
    }

    /**
     * creating new account based on body send from post method
     *
     * @param account
     * @return created account
     */
    public Account creatingAccount(Account account) {
        if (accountRepository.existsById(account.getId_account())) {
            throw new IllegalStateException("Account already exist");
        }
        var accountCreated = accountRepository.save(account);
        accountRepository.saveToAccountDebt(account.getId_account(), 0, 0);

        logger.info("Account " + accountCreated.getAccount_name() + " created");
        return accountCreated;
    }

    /**
     * updating whole account from the beginning, method more applicable for being a tool
     * method updateDebtOfAccounts and method summWholeBill from BillService class are better in use
     */
    public void updateAccount(int id_account, Bill bill) {
        //FIXED: 2021-10-17
        //NOW :
        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account with given id : " + id_account + " does not exist");
        }
        var account = accountRepository.findById(id_account).get();
        var balance = bill.getProducts()
                .stream()
                .mapToDouble(Product::getProduct_price)
                .sum();
        updateBalanceForAccount.updateBalanceForAccount(account, balance);
    }
    /**
     * based on bill given and list of accounts, method will calculate how much other accounts owe to the account which pay for bill.
     *
     * @param bill
     */
    public void updateDebtOfAccounts(Bill bill) {

        Map<Integer, Set<Product>> productsMap = new HashMap<>();
        List<Account> accountList = accountRepository.findAll();
        accountList.removeIf(account ->
                account.getId_account() == bill.getAccount().getId_account()); //removing account which pay for bill(because it is not needed for operations)
        /**
         * filling the map with integer as key and set of products as value
         */
        accountList.forEach(account -> {
            var set = bill.getProducts()
                    .stream()
                    .filter(product -> product.getAccount().getId_account() == account.getId_account())
                    .collect(Collectors.toSet());
            productsMap.put(account.getId_account(), set);
        });
        /**
         * calculating sum of whole set for all accounts in list, then updating row in column debt for each account id in the list
         */
        accountList.forEach(account -> {
            var debt = productsMap.get(account.getId_account())
                    .stream().mapToDouble(Product::getProduct_price)
                    .sum();
            if (debt == 0) { //that means account is not mentioned on bill

            } else if (!accountRepository.existsById_debtor(account.getId_account(), bill.getAccount().getId_account())) {
                var total = debt + accountRepository.getAccountDebtById(account.getId_account(), bill.getAccount().getId_account());
                   /* new code down here - using new repository*/
                accountDebtFacade.updateDebt(account, bill, total);
            } else {
                /*new code down here - using new repository */
                accountDebtFacade.saveNewDebt(account, bill, debt);
            }
        });
    }

    public Account updateFrom(int id, Account toUpdate) {
        if (!accountRepository.existsById(id)) {
            throw new IllegalArgumentException("no account with id:" + id + " found");
        }
        var account = accountRepository.findById(id).get();
        account.updateFrom(toUpdate);
        var updated = accountRepository.save(account);
        return updated;
    }

    //:FIXME

    /**
     * deleting account with every constraints
     * @param id_account
     */
    public void deleteAccount(int id_account) {
        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("no account with id:" + id_account + " found");
        }
        /*all of these should be managed by cascade delete (whenever the idAccount fk existed, that row will be deleted),still not working*/
        accountRepository.deleteAccountDebtById(id_account);
        accountRepository.deleteWholeDebt(id_account);
        accountRepository.deleteFromBillHistory(id_account);
        accountRepository.deleteFromProductHistory(id_account);
        accountRepository.deleteById(id_account);


    }

    /**
     * method should return map with accounts to which person owe money
     *
     * @param account
     * @return
     */
    //TODO create method with instruction given above
    Map<String, Double> getBalanceByAccount(Account account) {
        Map<String, Double> balance = new HashMap<>();
        return balance;
    }
}

