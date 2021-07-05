package com.example.monthlyexpensesapp.controllers;

import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.models.Product;
import com.example.monthlyexpensesapp.models.Shop;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

class ProductControllerTest {

    @Test
    void createProduct() {

        //given
      //  var productGroupWrite = mock(ProductsGroupWriteModel.class);
        var productRepo = mock(ProductRepository.class);
        var product = mock(Product.class);
        var shop = mock(Shop.class);



        //then



    }
}