package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.adapter.ShopRepository;
import com.example.monthlyexpensesapp.logic.BillWriteModel;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.models.Shop;
import com.example.monthlyexpensesapp.services.AccountService;
import com.example.monthlyexpensesapp.services.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//TODO: need to get List<Shop> List<Account> , add it to the <select> in html, so it can be find via model method
@Controller
public class AddingBillController {

    private BillService billService;
    private ShopRepository shopRepository;
    private AccountService accountService;
    private CategoryRepository categoryRepository;

    public AddingBillController(BillService billService, ShopRepository shopRepository, AccountService accountService, CategoryRepository categoryRepository) {
        this.billService = billService;
        this.shopRepository = shopRepository;
        this.accountService = accountService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/create-bill")
    public String showBill(Model model) {

        model.addAttribute("shops", getShops());
        model.addAttribute("accounts", getAccounts());
        model.addAttribute("categories", getCategories());
        model.addAttribute("dates", getDates());
        model.addAttribute("new_bill", new BillWriteModel());
        return "billCreator";
    }

    /**
     * adding new template for product specifics, every time post request is called
     * Account name and category name are passed as String, and managed by repositories
     *
     * @param current
     * @return
     */
    @PostMapping(params = "addProduct",value = "create-bill")
    String addProjectStep(@ModelAttribute("new_bill") BillWriteModel current, Model model) {

        current.getProductList().forEach(product -> {
            product.setAccount(accountService.getAccountByName(product.getAccount().getAccount_name()).get());
            product.setCategory(categoryRepository.findByName(product.getCategory().getCategory_name()).get());
        });
        current.getProductList().add(new Product());
        model.addAttribute("shops", getShops());
        model.addAttribute("accounts", getAccounts());
        model.addAttribute("categories", getCategories());
        model.addAttribute("dates", getDates());
        return "billCreator";

    }

    /**
     * Creating new Bill, adding collection of Product type to newly created instance of Bill class.
     * Repositories and services interpret the Strings passed by template and create instances of
     * classes needed to link product with account&category
     * @param
     * @return
     */
    @PostMapping("/create-bill")
    public String addBill(@ModelAttribute("new_bill") BillWriteModel billWriteModel,
                          @RequestParam("account_name") String account_name,
                          @RequestParam("shop_name") String shop_name,
                          @RequestParam("bill_date") String date, Model model) {

        var products_list = billWriteModel.getProductList();
        products_list.forEach(product -> {
            product.setAccount(accountService.getAccountByName(product.getAccount().getAccount_name()).get());
            product.setCategory(categoryRepository.findByName(product.getCategory().getCategory_name()).get());
        });
        var account = accountService.getAccountByName(account_name).get();
        var shop = shopRepository.findByName(shop_name).get();
        var bill = billService.openbill(shop.getId_shop(), account.getId_account(), LocalDate.parse(date));
        bill.setProducts(new HashSet<>(products_list));
        products_list.forEach(product -> {
            product.setBill(bill);
            billService.addProductToExistingBill(product, product.getCategory().getId_category(), bill.getId_bill(), product.getAccount().getId_account());
        });
        billService.toogleBill(bill.getId_bill());
        accountService.updateDebtOfAccounts(bill);

        model.addAttribute("message", "bill added");
        model.addAttribute("shops", getShops());
        model.addAttribute("accounts", getAccounts());
        model.addAttribute("categories", getCategories());
        model.addAttribute("dates", getDates());
        model.addAttribute("new_bill", new BillWriteModel());
        return "billCreator";
    }

    private List<Shop> getShops() {
        return shopRepository.findAll();
    }

    private List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    private List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    private List<LocalDate> getDates(int... range) {
        var dates = new ArrayList<LocalDate>();
        if (range.length == 0) {
            for (int i = 0; i < 31; i++) {
                dates.add(LocalDate.now().minusDays(i));
            }
            return dates;
        } else {
            for (int i = 0; i < range.length; i++) {
                dates.add(LocalDate.now().minusDays(i));
            }
            return dates;
        }
    }


}
