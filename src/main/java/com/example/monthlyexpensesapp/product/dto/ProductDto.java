package com.example.monthlyexpensesapp.product.dto;


import com.example.monthlyexpensesapp.bill.dto.BillDto;
import com.example.monthlyexpensesapp.category.dto.CategoryDto;

public interface ProductDto {

    int getId_product();
    String product_name();
    String product_note();
    double product_price();
    BillDto getBill();
    CategoryDto getCategory();

}
