package com.example.monthlyexpensesapp.bill;

import com.example.monthlyexpensesapp.account.AccountRepository;
import com.example.monthlyexpensesapp.bill.dto.BillDto;
import com.example.monthlyexpensesapp.product.ProductRepository;
import com.example.monthlyexpensesapp.shop.ShopRepository;
import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.product.ProductService;
import com.example.monthlyexpensesapp.shop.Shop;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillServiceTest {

    @Test
    void createBill() throws NoSuchFieldException, IllegalAccessException {

        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var bill = new Bill();
        bill.setGroup_date(LocalDate.of(2021, 7, 5));
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        var shop = new Shop();
        shop.setShop_name("foo");
        Field readField = shop.getClass().getDeclaredField("id_shop");
        readField.setAccessible(true);
        readField.set(shop, 1);

        var account = new Account();
        account.setAccount_name("foobar");
        Field readField_account = account.getClass().getDeclaredField("id_account");
        readField_account.setAccessible(true);
        readField_account.set(account, 1);

        var billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);

        //when
        when(billRepo.existsById(1)).thenReturn(false);
        when(shopRepo.existsById(1)).thenReturn(true);
        when(accountRepo.existsById(1)).thenReturn(true);
        when(shopRepo.findById(1)).thenReturn(Optional.of(shop));
        when(accountRepo.findById(1)).thenReturn(Optional.of(account));
        when(billRepo.save(bill)).thenReturn(bill);


        //then
        assertThat(shop.getShop_name()).isEqualTo("foo");
        assertThat(account.getAccount_name()).isEqualTo("foobar");
        assertEquals(account.getId_account(), 1);
        assertEquals(shop.getId_shop(), 1);
        assertEquals(bill.getId_bill(), 1);

        //under test
        billService.openbill(1, 1,LocalDate.now());

    }

    @Test
    void checkIfDeletingbillIsworking_andDeletingAllProductsAssociated() throws NoSuchFieldException, IllegalAccessException {

        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var bill = new Bill();

        bill.setGroup_date(LocalDate.of(2021, 7, 5));
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
        bill.setProducts(products);
        //then
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        when(billRepo.existsById(1)).thenReturn(true);
        when(billRepo.findById(1)).thenReturn(Optional.of(bill));
        //under test
        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        billService.deleteBill(1);
    }

    @Test
    void checkIfSumWholeBill_isWorking() throws IllegalAccessException, NoSuchFieldException {
        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var bill = new Bill();
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
        products.stream().forEach(product -> product.setProduct_price(20.0));
        bill.setProducts(products);

        //then
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        when(billRepo.existsById(1)).thenReturn(true);
        when(billRepo.findById(1)).thenReturn(Optional.of(bill));

        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        billService.sumWholeBill(bill);
    }

    @Test
    void returningAllProductsLinkedWithBill() throws NoSuchFieldException, IllegalAccessException {
        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        Bill bill = new Bill();
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
        products.stream().forEach(product -> product.setProduct_price(20.0));
        bill.setProducts(products);
        //then
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        when(billRepo.existsById(1)).thenReturn(true);
        when(billRepo.findById(1)).thenReturn(Optional.of(bill));

        //test
        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        billService.getAllProducts(1);

    }

    @Test
    void checkIfupdatingProductFromBill_throwIllegalStateException_whenWrongBillId() throws NoSuchFieldException, IllegalAccessException {

        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var product = new Product();
        Field getProductId = product.getClass().getDeclaredField("id_product");
        getProductId.setAccessible(true);
        getProductId.set(product, 1);
        Bill bill = new Bill();
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(product);
        bill.setProducts(products);

        //then
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        assertThat(product.getId_product()).isEqualTo(1);
        when(billRepo.existsById(1)).thenReturn(true);
        assertThat(product).isEqualTo(product);
        when(billRepo.findById(1)).thenReturn(Optional.of(bill));
        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));


        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        Exception exceptionBillWrongId = assertThrows(IllegalStateException.class,
                () -> billService.updateProduct(2, product));
        Exception exceptionProductDoesNotExist = assertThrows(IllegalStateException.class,
                () -> billService.updateProduct(1, new Product()));
        String message_bill = exceptionBillWrongId.getMessage();
        String message_product = exceptionProductDoesNotExist.getMessage();
        //under test
        assertThat(message_bill).isEqualTo(exceptionBillWrongId.getMessage());
        assertThat(message_product).isEqualTo(exceptionProductDoesNotExist.getMessage());
        assertDoesNotThrow(() -> billService.updateProduct(1, product));

    }

    @Test
    void checkIfToogleBillMethod_throwIllegalArgumentException_ifWrongBillIdIsPassed() throws NoSuchFieldException, IllegalAccessException {
        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var product = new Product();
        Field getProductId = product.getClass().getDeclaredField("id_product");
        getProductId.setAccessible(true);
        getProductId.set(product, 1);
        Bill bill = new Bill();
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(product);
        bill.setProducts(products);
        //then
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        when(billRepo.existsById(1)).thenReturn(false);
        //under test
        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        Exception exceptionWrongId = assertThrows(IllegalArgumentException.class, () -> billService.toggleBill(1));
        String message = exceptionWrongId.getMessage();
        assertThat(message).isEqualTo(exceptionWrongId.getMessage());
    }

    @Test
    void checkIfToogleBillMethod_doesNotThrowIllegalArgumentException_ifIdExist() throws NoSuchFieldException, IllegalAccessException {
        //given
        var shopRepo = mock(ShopRepository.class);
        var billRepo = mock(BillRepository.class);
        var accountRepo = mock(AccountRepository.class);
        var productRepository = mock(ProductRepository.class);
        var productService = mock(ProductService.class);
        var product = new Product();
        Field getProductId = product.getClass().getDeclaredField("id_product");
        getProductId.setAccessible(true);
        getProductId.set(product, 1);
        Bill bill = new Bill();
        Field getIdField = bill.getClass().getDeclaredField("id_bill");
        getIdField.setAccessible(true);
        getIdField.set(bill, 1);
        Set<Product> products = new HashSet<>();
        products.add(product);
        bill.setProducts(products);
        //given
        assertThat(bill.getId_bill()).isEqualTo(1);
        assertThat(bill.getProducts()).isEqualTo(products);
        when(billRepo.existsById(1)).thenReturn(true);
        when(billRepo.findById(1)).thenReturn(Optional.of(bill));
        //test
        BillService billService = new BillService(billRepo, shopRepo, accountRepo, productRepository, productService);
        assertDoesNotThrow(() -> billService.toggleBill(1));
    }

    @Test
    void shortTestBillConbstructor() {
        Bill bill = new Bill();
        assertThat(bill.isClosed()).isEqualTo(false);

    }
}