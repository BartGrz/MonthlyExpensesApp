package com.example.monthlyexpensesapp.product;

import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.category.CategoryRepository;
import com.example.monthlyexpensesapp.bill.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private BillRepository billRepository;
    private CategoryRepository categoryRepository;
    private AccountRepository accountRepository;

    public ProductService(ProductRepository productRepository, BillRepository billRepository, CategoryRepository categoryRepository, AccountRepository accountRepository) {
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    public List<Product> readAllProducts() {
        return productRepository.findAll();
    }

    public Product readProductById(int id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with given id" + id + " not found");
        }
        return productRepository.findById(id).get();
    }

    public Product updateProduct(Product product) {
        var updated = productRepository.save(product);
        return updated;
    }

    public Product addProductToExistingBill(Product toCreate, int id_bill, int id_account) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalArgumentException("bill with given id does not exist");
        }
        if (!categoryRepository.existsById(toCreate.getCategory().getId_category())) {
            throw new IllegalArgumentException("category with given id does not exist");
        }
        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account with given id does not exist");
        }
        var bill = billRepository.findById(id_bill).get();
        var category = categoryRepository.findById(toCreate.getCategory().getId_category()).get();
        var account = accountRepository.findById(id_account).get();
        toCreate.setCategory(category);
        toCreate.setBill(bill);
        toCreate.setAccount(account);
        var created = productRepository.save(toCreate);
        return created;
    }
    public Product addProductToExistingBill(Product toCreate,int id_category, int id_bill, int id_account) {

        if (!billRepository.existsById(id_bill)) {
            throw new IllegalArgumentException("bill with given id does not exist");
        }
        if (!categoryRepository.existsById(id_category)) {
            throw new IllegalArgumentException("category with given id does not exist");
        }
        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account with given id does not exist");
        }
        var bill = billRepository.findById(id_bill).get();
        var category = categoryRepository.findById(id_category).get();
        var account = accountRepository.findById(id_account).get();
        toCreate.setCategory(category);
        toCreate.setBill(bill);
        toCreate.setAccount(account);
        var created = productRepository.save(toCreate);
        return created;
    }

    public Product deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with given id" + id + " not found");
        }
        var product = productRepository.findById(id).get();
        productRepository.deleteById(product.getId_product());
        product.getBill().getProducts().remove(product);
        logger.warn("Product with given id =" + id + " was deleted from bill with id =" + product.getBill().getId_bill());

        return product;
    }


}


/*
 /**
     * creating new bill and adding product passed via POST method into products list. Maybe usefull later
     * @param toCreate
     * @param id_category
     * @param id_shop
     * @param id_account
     * @param date
     * @return

  public Product addProductAndCreateBill(Product toCreate, int id_category, int id_shop, int id_account, LocalDate date) {

        if (!accountRepository.existsById(id_account)) {
            throw new IllegalArgumentException("account does not exist");
        }
        if (!categoryRepository.existsById(id_category)) {
            throw new IllegalArgumentException("category does not exist");
        }
        if (!categoryRepository.existsById(id_category)) {
            throw new IllegalArgumentException("category does not exist");
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
 */