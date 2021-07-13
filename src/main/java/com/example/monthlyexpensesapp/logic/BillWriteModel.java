package com.example.monthlyexpensesapp.logic;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDate;


@Component
public class BillWriteModel {
    private static Logger logger = LoggerFactory.getLogger(BillWriteModel.class);
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private AccountRepository accountRepository;
    private BillRepository billRepository;
    private ShopRepository shopRepository;

    public BillWriteModel(ProductRepository productRepository, CategoryRepository categoryRepository,
                          AccountRepository accountRepository, BillRepository billRepository,
                          ShopRepository shopRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.billRepository = billRepository;
        this.shopRepository = shopRepository;
    }

    public Product addProductAndCreateBill(Product toCreate, int id_category, int id_shop, int id_account, LocalDate date) {

        if (!accountRepository.existsById(id_account)) {
            logger.warn("account does not exist");
            return null;
        }
        if (!categoryRepository.existsById(id_category)) {
            logger.warn("category does not exist");
            return null;
        }
        if (!categoryRepository.existsById(id_category)) {
            logger.warn("category does not exist");
            return null;
        }
        var account = accountRepository.findById(id_account).get();
        var category = categoryRepository.findById(id_category).get();
        var shop = shopRepository.findById(id_shop).get();
        var bill = new Bill();
        bill.setAccount(account);
        bill.setShop(shop);
        bill.setGroup_date(date);
        billRepository.save(bill);
        logger.info("Bill created and saved " + bill.getId_bill());
        toCreate.setCategory(category);
        toCreate.setBill(bill);
        var product = productRepository.save(toCreate);

        logger.info("product created and added to bill  " + product.getId_product());

        return product;
    }

}
