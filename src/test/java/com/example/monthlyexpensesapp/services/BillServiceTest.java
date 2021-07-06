package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.AccountRepository;
import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.adapter.ShopRepository;
import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillServiceTest {

    @Test
    void createBill() throws NoSuchFieldException, IllegalAccessException {

        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var bill = new Bill();
        bill.setGroup_date(LocalDate.of(2021,7,5));
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill,1);
        var shop = new Shop();
        shop.setShop_name("foo");
        Field readField = shop.getClass().getDeclaredField("id_shop");
        readField.setAccessible(true);
        readField.set(shop,1);

        var account = new Account();
        account.setAccount_name("foobar");
        Field readField_account = account.getClass().getDeclaredField("id_account");
        readField_account.setAccessible(true);
        readField_account.set(account,1);

        var billService = new BillService(billRepo,shopRepo,accountRepo);

        //when
        when(billRepo.existsById(1)).thenReturn(true);
        when(shopRepo.existsById(1)).thenReturn(true);
        when(accountRepo.existsById(1)).thenReturn(true);
        when(shopRepo.findById(1)).thenReturn(Optional.of(shop));
        when(accountRepo.findById(1)).thenReturn(Optional.of(account));


        //then
        assertThat(shop.getShop_name()).isEqualTo("foo");
        assertThat(account.getAccount_name()).isEqualTo("foobar");
        assertEquals(account.getId_account(),1);
        assertEquals(shop.getId_shop(),1);
        assertEquals(bill.getId_bill(),1);

        //under test
        billService.createBill(bill,1,1);

    }
}