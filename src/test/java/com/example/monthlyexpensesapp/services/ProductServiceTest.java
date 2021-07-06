package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.models.Category;
import com.example.monthlyexpensesapp.models.Product;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProductServiceTest {


    @Test
    void checkIfAddingProductWithCreatingBill_works() {
        //given
        var billRepo = mock(BillRepository.class);
        var productRepo = mock(ProductRepository.class);
        var categpryRepo = mock(CategoryRepository.class);
        var product = new Product();


        product.setProduct_name("foo");


    }

}