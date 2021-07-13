package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.Bill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
    private BillRepository billRepository;
    private AccountRepository accountRepository;
    private ShopRepository shopRepository;
    private ProductRepository productRepository;

    public BillService(BillRepository billRepository, ShopRepository shopRepository,
                       AccountRepository accountRepository, ProductRepository productRepository
    ) {
        this.billRepository = billRepository;
        this.shopRepository = shopRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
    }

    public Bill openbill(Bill bill, int id_account, int id_shop) {
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
        bill.setShop(shop);
        var created =billRepository.save(bill);
        logger.info("bill created with id = " + created.getId_bill()+ " with shop " + shop.getShop_name() + " account " + account.getAccount_name());
        return bill;
    }
    public Bill deleteBill(int id_bill) {
        
        if(!billRepository.existsById(id_bill)) {
            logger.warn("Bill with given id" + id_bill+" not found");
            return null;
        }
        var bill = billRepository.findById(id_bill).get();
        int size = bill.getProducts().size();
        bill.getProducts().stream().forEach(product -> {
            productRepository.deleteById(product.getId_product());
        });
        billRepository.deleteById(bill.getId_bill());

        logger.info("Bill removed with " +size + " products "  );
        return bill;
    }
}
