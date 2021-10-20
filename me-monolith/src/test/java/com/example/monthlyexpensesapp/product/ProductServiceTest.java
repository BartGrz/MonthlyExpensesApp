package com.example.monthlyexpensesapp.product;

import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.bill.BillRepository;
import com.example.monthlyexpensesapp.category.CategoryRepository;
import com.example.monthlyexpensesapp.product.ProductRepository;
import com.example.monthlyexpensesapp.bill.Bill;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.product.ProductService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {


    @Test
    void checkIfDeletingProduct_works() throws IllegalAccessException, NoSuchFieldException {
        //given
        var billRepo = mock(BillRepository.class);
        var productRepo = mock(ProductRepository.class);
        var categpryRepo = mock(CategoryRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var product = new Product();
        var bill = new Bill();
        Set<Product> set = new HashSet<>();

        bill.setProducts(set);
        set.add(product);
        Field getId = product.getClass().getDeclaredField("id_product");
        getId.setAccessible(true);
        getId.set(product,1);
        product.setBill(bill);

        //then
        assertThat(product.getId_product()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(set);
        assertThat(product.getBill()).isEqualTo(bill);

        when(productRepo.existsById(1)).thenReturn(true);
        when(productRepo.existsById(2)).thenReturn(false);
        when(productRepo.findById(1)).thenReturn(Optional.of(product));

        //system under test
        ProductService productService = new ProductService(productRepo,billRepo,categpryRepo,accountRepo);
        productService.deleteProduct(1);


    }

}