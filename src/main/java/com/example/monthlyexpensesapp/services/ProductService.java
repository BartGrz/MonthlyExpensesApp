package com.example.monthlyexpensesapp.services;

import com.example.monthlyexpensesapp.adapter.CategoryRepository;
import com.example.monthlyexpensesapp.adapter.ProductRepository;
import com.example.monthlyexpensesapp.adapter.BillRepository;
import com.example.monthlyexpensesapp.models.Bill;
import com.example.monthlyexpensesapp.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private BillRepository billRepository;
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, BillRepository billRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product addProductToExistingBill(Product toCreate, int id_category, int id_bill) {

        if (!billRepository.existsById(id_bill)) {
            logger.warn("bill with given id does not exist");
            return null;
        }
        if (!categoryRepository.existsById(id_category)) {
            logger.warn("category with given id does not exist");
            return null;
        }
        var bill = billRepository.findById(id_bill).get();
        var category = categoryRepository.findById(id_category).get();
        toCreate.setCategory(category);
        toCreate.setBill(bill);
        productRepository.save(toCreate);
        return toCreate;

    }

    public Product deleteProduct(int id) {
        if(!productRepository.existsById(id)) {
            logger.warn("Product with given id" + id+" not found");
            return null;
        }
        var product = productRepository.findById(id).get();
        System.out.println(product.getId_product() + " " + product.getProduct_name());
        productRepository.deleteById(product.getId_product());
        product.getBill().getProducts().remove(product);
        logger.warn("Product with given id =" + id+" was deleted from bill with id =" + product.getBill().getId_bill());

        return product;
    }
}
