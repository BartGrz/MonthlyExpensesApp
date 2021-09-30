package com.example.monthlyexpensesapp.bill;


import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.product.ProductRepository;
import com.example.monthlyexpensesapp.product.ProductService;
import com.example.monthlyexpensesapp.shop.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private ProductService productService;

    public BillService(BillRepository billRepository, ShopRepository shopRepository,
                       AccountRepository accountRepository, ProductRepository productRepository,
                       ProductService productService) {
        this.billRepository = billRepository;
        this.shopRepository = shopRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }


    public Bill openbill(int id_shop, int id_account, LocalDate localDate) {

        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account does not exist");
        }
        if (!shopRepository.existsById(id_shop)) {
            throw new IllegalArgumentException("shop does not exist");
        }
        var account = accountRepository.findById(id_account).get();
        var shop = shopRepository.findById(id_shop).get();
        var bill = new Bill();
        bill.setAccount(account);
        bill.setShop(shop);
        bill.setGroup_date(localDate);
        var created = billRepository.save(bill);
        billRepository.saveBilltoBillSum(created.getId_bill(), 0);
        logger.info("bill created with id = " + created.getId_bill() + " with shop " + shop.getShop_name() + " account " + account.getAccount_name());
        return created;
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
     *
     * @param bill
     */
    public void sumWholeBill(Bill bill) {

        if (bill.getProducts().isEmpty()) {
            throw new IllegalStateException("Bill does not have products");
        }
        double res = bill.getProducts().stream().mapToDouble(value -> value.getProduct_price()).sum();
        billRepository.sumAllAmongBill(res, bill.getId_bill());
        logger.info("sum for bill id = " + bill.getId_bill() + " updated");
        var sum = billRepository.getBillSumById(bill.getId_bill());
        // var balance = sum+ accountRepository.getAccountDebtById(bill.getAccount().getId_account());
        accountRepository.updateAccountBalance(sum, bill.getAccount().getId_account());
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
        bill.setClosed(false);
        var product = bill.getProducts().stream().filter(prod -> prod.getId_product() == toUpdate.getId_product()).findAny().get();
        product.updateFrom(toUpdate);
        var updated = productRepository.save(product);
        logger.info("success ! product with id =" + product.getId_product() + " from bill with id=" + id_bill + " changed ");
        return updated;
    }

    public void toggleBill(int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalArgumentException("there is no bill with given id");
        }

        var bill = billRepository.findById(id_bill).get();
        if (bill.isClosed()) {
            throw new IllegalStateException("Bill is already closed");
        }
        bill.setClosed(true);
        var updated = billRepository.save(bill);
        logger.info("Bill is closed successfully, sum will be calculated");
        sumWholeBill(updated);
    }
    public void addProductToExistingBill(Product toCreate, int id_category, int id_bill,int id_account) {

      productService.addProductToExistingBill(toCreate,id_category,id_bill,id_account);
    }
    public void addProductToExistingBill(Product toCreate, int id_bill,int id_account) {

        productService.addProductToExistingBill(toCreate,id_bill,id_account);
    }
}

