package com.example.monthlyexpensesapp.logic;

import com.example.monthlyexpensesapp.adapter.*;
import com.example.monthlyexpensesapp.models.*;
import com.example.monthlyexpensesapp.services.ProductService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillWriteModelTest {

    /*
    @Test
    void addProductAndCreateBill() throws NoSuchFieldException, IllegalAccessException {
        //given
        var billRepo = mock(BillRepository.class);
        var productRepo = mock(ProductRepository.class);
        var categoryRepo = mock(CategoryRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var shopRepo = mock(ShopRepository.class);
        Set<Product> products = new HashSet<>();
        var account = new Account();
        account.setAccount_name("foobar");
        Field readField_account = account.getClass().getDeclaredField("id_account");
        readField_account.setAccessible(true);
        readField_account.set(account,1);

        var product = new Product();
        Field getProductId = product.getClass().getDeclaredField("id_product");
        getProductId.setAccessible(true);
        getProductId.set(product,1);
        product.setProduct_name("foo");
        product.setProduct_price(25.0);
        product.setAccount(account);
        product.setProduct_note("foo");

        var bill = new Bill();
        Field getBillId = bill.getClass().getDeclaredField("id_bill");
        getBillId.setAccessible(true);
        getBillId.set(bill,1);

        var shop = new Shop();
        shop.setShop_name("foo");
        Field readField = shop.getClass().getDeclaredField("id_shop");
        readField.setAccessible(true);
        readField.set(shop,1);

        var category = new Category();
        Field getCategoryId = category.getClass().getDeclaredField("id_category");
        getCategoryId.setAccessible(true);
        getCategoryId.set(category,1);

        assertThat(product.getId_product()).isEqualTo(1);
        assertThat(account.getId_account()).isEqualTo(1);
        assertThat(shop.getId_shop()).isEqualTo(1);
        assertThat(category.getId_category()).isEqualTo(1);
        assertThat(bill.getId_bill()).isEqualTo(1);

        when(categoryRepo.existsById(1)).thenReturn(true);
        when(accountRepo.existsById(1)).thenReturn(false);
        when(shopRepo.existsById(1)).thenReturn(true);
        when(accountRepo.findById(1)).thenReturn(Optional.of(account));
        when(categoryRepo.findById(1)).thenReturn(Optional.of(category));
        when(shopRepo.findById(1)).thenReturn(Optional.of(shop));
        when(productRepo.save(product)).thenReturn(product);



        BillWriteModel billWriteModel = new BillWriteModel(productRepo,categoryRepo,accountRepo,billRepo,shopRepo);
        billWriteModel.addProductAndCreateBill(
                product,1,1,1, LocalDate.of(2021,7,6)
        );
    }

     */
}