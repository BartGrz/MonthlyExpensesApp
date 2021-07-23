package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        var created = billRepository.save(bill);
        logger.info("bill created with id = " + created.getId_bill() + " with shop " + shop.getShop_name() + " account " + account.getAccount_name());
        return bill;
    }

    public Bill deleteBill(int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalStateException("Bill with given id" + id_bill + " not found");
        }
        var bill = billRepository.findById(id_bill).get();
        int size = bill.getProducts().size();
        bill.getProducts().stream().forEach(product -> {
            productRepository.deleteById(product.getId_product());
        });
        billRepository.deleteById(bill.getId_bill());

        logger.info("Bill removed with " + size + " products ");
        return bill;
    }

    public void sumWholeBill(int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalStateException("Bill with given id :" + id_bill + " does not exist");
        } else {
            var bill = billRepository.findById(id_bill).get();
            double res = bill.getProducts().stream().mapToDouble(value -> value.getProduct_price()).sum();
            billRepository.sumAllAmongBill(res, id_bill);
            logger.info("sum for bill id = " + id_bill + " updated");
        }
    }
    public List<Product> getAllProducts(int id_bill) {

        if(!billRepository.existsById(id_bill)) {
            return Collections.emptyList();
        }
        var bill = billRepository.findById(id_bill).get();
        logger.info("returned list with " + bill.getProducts().size());

        return bill.getProducts().stream().collect(Collectors.toList());
    }
    public List<Bill> getAllBills() {

        return billRepository.findAll();
    }
    public Product updateProduct(int id_bill, Product toUpdate) {
    
        if (!billRepository.existsById(id_bill)) {
            throw new IllegalStateException("there is no bill with given id");
        }
        if(!productRepository.existsById(toUpdate.getId_product())) {
            throw new IllegalStateException("product with  given id="+toUpdate.getId_product()+" does not exist ");
        }
        var bill = billRepository.findById(id_bill).get();
        var product = bill.getProducts().stream().filter(prod -> prod.getId_product()==toUpdate.getId_product()).findAny().get();
        
            product.updateFrom(toUpdate);
            var updated = productRepository.save(product);
            logger.info("success ! product with id ="+product.getId_product()+ " from bill with id="+id_bill+" changed ");
            return updated;
        }
    }

