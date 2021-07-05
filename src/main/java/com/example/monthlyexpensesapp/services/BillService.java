package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
    private BillRepository billRepository;
    private AccountRepository accountRepository;
    private ShopRepository shopRepository;

    public BillService(BillRepository billRepository, ShopRepository shopRepository, AccountRepository accountRepository) {
        this.billRepository = billRepository;
        this.shopRepository = shopRepository;
        this.accountRepository = accountRepository;
    }

    public Bill createBill(Bill bill, int id_account, int id_shop) {
        if (billRepository.existsById(bill.getId_bill())) {
            logger.warn("Bill already exist");
            return null;
        }
        if (!accountRepository.existsById(id_account)) {
            logger.warn("account does not exist");
            return null;
        }
        if (!shopRepository.existsById(id_shop)) {
            logger.warn("shop does not exist");
            return null;
        }
        var account = accountRepository.findById(id_account).get();
        var shop = shopRepository.findById(id_shop).get();
        bill.setAccount(account);
        logger.info("account added "+ account.getId_account() + " name : " + account.getAccount_name());
        bill.setShop(shop);
        logger.info("shop added "+ shop.getId_shop() + " name : " + shop.getShop_name());
        billRepository.save(bill);

        return bill;
    }

}
