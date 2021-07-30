package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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


    public Bill openbill(Bill bill, int id_shop, int id_account) {

        if (billRepository.existsById(bill.getId_bill())) {
            throw new IllegalArgumentException("Bill already exist");
        }
        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account does not exist");
        }
        if (!shopRepository.existsById(id_shop)) {
            throw new IllegalArgumentException("shop does not exist");
        }
        var account = accountRepository.findById(id_account).get();
        var shop = shopRepository.findById(id_shop).get();
        bill.setAccount(account);
        bill.setShop(shop);
        var created = billRepository.save(bill);
        billRepository.saveBilltoBillSum(created.getId_bill(), 0);
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
        billRepository.deleteFromBillSumBillById(id_bill);
        billRepository.deleteById(bill.getId_bill());

        logger.info("Bill removed with " + size + " products ");
        return bill;
    }

    /**
     * summing total cost and updating balance of the account of a bill payer
     * @param bill
     */
    public void sumWholeBill(Bill bill) {

        if(bill.getProducts().isEmpty()) {
            throw new IllegalStateException("Bill does not have products");
        }
        double res = bill.getProducts().stream().mapToDouble(value -> value.getProduct_price()).sum();
        billRepository.sumAllAmongBill(res, bill.getId_bill());
        logger.info("sum for bill id = " + bill.getId_bill() + " updated");
        var sum = billRepository.getBillSumById(bill.getId_bill());
        var balance = sum+ accountRepository.getAccountDebtById(bill.getAccount().getId_account());
        accountRepository.updateAccountBalance(balance, bill.getAccount().getId_account());

    }

    public List<Product> getAllProducts(int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            return Collections.emptyList();
        }
        var bill = billRepository.findById(id_bill).get();
        logger.info("returned list with " + bill.getProducts().size());

        return bill.getProducts().stream().collect(Collectors.toList());
    }

    public List<Bill> getAllBills() {

        return billRepository.findAll();
    }

    public Bill getBill(int id) {
        if (!billRepository.existsById(id)) {
            throw new IllegalArgumentException("there is no bill with given id " + id);
        }
        return billRepository.findById(id).get();
    }

    public Product updateProduct(int id_bill, Product toUpdate) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalStateException("there is no bill with given id");
        }
        if (!productRepository.existsById(toUpdate.getId_product())) {
            throw new IllegalStateException("product with  given id=" + toUpdate.getId_product() + " does not exist ");
        }
        var bill = billRepository.findById(id_bill).get();
        var product = bill.getProducts().stream().filter(prod -> prod.getId_product() == toUpdate.getId_product()).findAny().get();

        product.updateFrom(toUpdate);
        var updated = productRepository.save(product);
        logger.info("success ! product with id =" + product.getId_product() + " from bill with id=" + id_bill + " changed ");
        return updated;
    }

    public void toogleBill(int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalArgumentException("there is no bill with given id");
        }
        var bill = billRepository.findById(id_bill).get();
        bill.set_closed(true);
        billRepository.save(bill);

        if (!bill.is_closed()) {
            throw new IllegalStateException("Bill is already closed");
        }
        logger.info("Bill is closed successfully, sum will be calculated");
        sumWholeBill(bill);
    }
}

