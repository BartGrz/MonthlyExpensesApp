package com.example.monthlyexpensesapp.bill;


import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.account.AccountService;
import com.example.monthlyexpensesapp.billSum.BillSumFacade;
import com.example.monthlyexpensesapp.billSum.dto.BillSumDto;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.product.ProductRepository;
import com.example.monthlyexpensesapp.product.ProductService;
import com.example.monthlyexpensesapp.shop.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
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
    private final AccountService accountService;
    private final BillSumFacade billSumFacade;

    public BillService(BillRepository billRepository, ShopRepository shopRepository,
                       AccountRepository accountRepository, ProductRepository productRepository,
                       ProductService productService, final AccountService accountService, final BillSumFacade billSumFacade) {
        this.billRepository = billRepository;
        this.shopRepository = shopRepository;
        this.accountRepository = accountRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.accountService = accountService;
        this.billSumFacade = billSumFacade;
    }


    Bill openBill(int id_shop, int id_account, LocalDate localDate) {

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
        bill.setProducts(new HashSet<>());
        var created = billRepository.save(bill);
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
        billSumFacade.deleteBillSum(id_bill);
        billRepository.deleteById(bill.getId_bill());

        logger.info("Bill removed with " + size + " products ");
        return bill;
    }

    /**
     * summing total cost and updating balance of the account of a bill payer
     *
     * @param bill
     */
    //TODO : AccountBalamnceFacade method in accountService need to changed,
    // called to many times and calculating the same thing which is already done here
    public void sumWholeBill(Bill bill) {
        if (bill.getProducts().isEmpty()) {
            throw new IllegalStateException("Bill does not have products");
        }
        billSumFacade.saveNewBillSum(bill);
        logger.info("sum for bill id = " + bill.getId_bill() + " updated");
        //:TODO update accounts balances
        //
        accountService.updateAccount(bill.getAccount().getId_account(),bill);
        var sum = billSumFacade.getSumBy(bill);
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
        sumWholeBill(updated);
    }
    public void addProductToExistingBill(Product toCreate, int id_category, int id_bill,int id_account) {

      productService.addProductToExistingBill(toCreate,id_category,id_bill,id_account);
    }
    public void addProductToExistingBill(Product toCreate, int id_bill,int id_account) {

        productService.addProductToExistingBill(toCreate,id_bill,id_account);
    }
}

