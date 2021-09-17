package com.example.monthlyexpensesapp.logic;

import com.example.monthlyexpensesapp.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BillWriteModel {

    @Getter
    List<Product> productList = new ArrayList<>() ;
    @Getter
    @Setter
    public String shop_name;
    @Getter
    @Setter
    public String account_name;
    @Getter
    @Setter
    public String bill_date;

    /**
     * when called automatically adding new Product to collection
     */
    public BillWriteModel() {
        productList.add(new Product());
    }
}
