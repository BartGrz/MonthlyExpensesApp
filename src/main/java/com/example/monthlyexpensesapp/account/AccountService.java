package com.example.monthlyexpensesapp.account;


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

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Optional<Account> getAccountByName(String name) {

        var account = accountRepository.findByName(name).get();
        if(Optional.of(account).isEmpty()) {
            throw new IllegalArgumentException("category with given name does not exist");
        }
        return Optional.of(account);
    }

    /**
     * creating new account based on body send from post method
     * @param account
     * @return created account
     */
    public Account creatingAccount(Account account) {
        if (accountRepository.existsById(account.getId_account())) {
            throw new IllegalStateException("Account already exist");
        }
        var accountCreated = accountRepository.save(account);
        logger.info("Account " + accountCreated.getAccount_name() + " created");
        return accountCreated;
    }

    /**
     * updating whole account from the beginning, method more applicable for being a tool
     * method updateDebtOfAccounts and method summWholeBill from BillService class are better in use
     */
    public void updateAccount(int id_account) {

        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account with given id : " + id_account + " does not exist");
        }
        List<Account> accountList = accountRepository.findAll();
        var account = accountRepository.findById(id_account).get();
        accountList.removeIf(acc -> acc.getId_account() == id_account);

        accountList.forEach(acc -> acc.getBills().stream()
                .map(Bill::getProducts)
                .forEach(products -> {
                    var res = products.stream()
                            .mapToDouble(Product::getProduct_price).sum();
                    accountRepository.updateAccountDebt(res, acc.getId_account(), id_account);
                    logger.info("account debt with id:" + id_account + " has been updated");
                }));

        final double[] total_balance = {0};
        account.getBills().stream()
                .map(Bill::getProducts)
                .forEach(products -> {
                    var res = products.stream()
                            .filter(product -> product.getAccount().getId_account() == id_account)
                            .mapToDouble(Product::getProduct_price).sum();
                    total_balance[0] = res + total_balance[0];
                });

        accountRepository.updateAccountBalance(total_balance[0], id_account);
        logger.info("account balance with id:" + id_account + " has been updated");
    }

    /**
     * based on bill given and list of accounts, method will calculate how much other accounts owe to the account which pay for bill.
     *
     * @param bill
     */
    public void updateDebtOfAccounts(Bill bill) {

        Map<Integer, Set<Product>> productsMap = new HashMap<>();
        List<Account> accountList = accountRepository.findAll();
        accountList.removeIf(account -> account.getId_account() == bill.getAccount().getId_account()); //removing account which pay for bill(because it is not needed for operations)
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
            if(debt==0) { //that means account is not mentioned on bill

            } else if (!accountRepository.existsById_debtor(account.getId_account(), bill.getAccount().getId_account())) {
                var total = debt+accountRepository.getAccountDebtById(account.getId_account(),bill.getAccount().getId_account());
                accountRepository.updateAccountDebt(total, bill.getAccount().getId_account(), account.getId_account());
                logger.warn(  account.getAccount_name()  + "  owe " + total+ " int total to " + bill.getAccount().getAccount_name());
            } else {
                accountRepository.saveToAccountDebt(account.getId_account(), bill.getAccount().getId_account(), debt);
                logger.info("adding new debt , account : " + account.getAccount_name() + " owes  " + debt + " to " + bill.getAccount().getAccount_name());
            }
        });

    }
    public Account updateFrom(int id, Account toUpdate) {
        if(!accountRepository.existsById(id)){
            throw new IllegalArgumentException("no account with id:"+id + " found");
        }
        var account = accountRepository.findById(id).get();
        account.updateFrom(toUpdate);
        return account;
    }
}
