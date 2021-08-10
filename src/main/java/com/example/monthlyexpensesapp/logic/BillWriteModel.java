package com.example.monthlyexpensesapp.logic;

import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.models.Shop;
import com.example.monthlyexpensesapp.services.BillService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class BillWriteModel {

    @Getter
    List<Product> productList = new ArrayList<>() ;
    private  BillService billService;
    @Getter
    @Setter
    public String shop_name;
    @Getter
    @Setter
    public String account_name;
    @Getter
    @Setter
    public String date;

    @Autowired
    public void setBillService(BillService billService) {
        this.billService = billService;
    }

    public BillWriteModel() {
        productList.add(new Product());
    }

    public Bill toBill(Account account, Shop shop) {
        if (billService==null) {
            throw new IllegalStateException("billService has null value");
        }
        var bill =  billService.openbill(shop.getId_shop(),account.getId_account(),LocalDate.now());
        productList.forEach(product -> billService.addProductToExistingBill(product,bill.getId_bill(),product.getAccount().getId_account()));
        return bill;
    }
}
