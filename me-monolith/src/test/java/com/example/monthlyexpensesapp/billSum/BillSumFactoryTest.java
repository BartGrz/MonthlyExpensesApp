package com.example.monthlyexpensesapp.billSum;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.bill.Bill;
import com.example.monthlyexpensesapp.billSum.dto.BillSumDto;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.shop.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;

class BillSumFactoryTest {


    @Test
    @Description(value = "check if the factory is returning the right instance of class")
    void checkIfFactoryIsWorkingCorrectly() throws NoSuchFieldException, IllegalAccessException {

        //given
        var bill = new Bill();
        Field id = bill.getClass().getDeclaredField("id_bill");
        id.setAccessible(true);
        id.set(bill, 1);
        var shop = new Shop();
        var account = new Account();
        account.setAccount_name("bar");
        shop.setShop_name("foo");
        bill.setShop(shop);
        bill.setAccount(account);
        double sum = 20;
        var deser = BillSumDto.deserialize(bill.getId_bill(), sum, bill);

        //then
        assertThat(deser.getBill()).isEqualTo(bill);
        assertThat(deser.getId_bill()).isEqualTo(1);
        assertThat(deser.getSum()).isEqualTo(sum);

        //under test
        assertDoesNotThrow(() -> {
            var newBillSum = BillSumFactory.from(deser);
            System.out.println(newBillSum);
        });


    }


}