package com.example.monthlyexpensesapp.bill.dto;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.shop.Shop;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BillDto {
    @Getter
    private List<Product> products = new ArrayList<>();
    @Getter
    public String bill_date;
    @Getter
    private String shop_name;
    @Getter
    private String account_name;

    public BillDto() {
        products.add(new Product());
    }

}
