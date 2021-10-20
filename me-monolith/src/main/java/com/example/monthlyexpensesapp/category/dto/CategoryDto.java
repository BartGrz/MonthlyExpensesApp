package com.example.monthlyexpensesapp.category.dto;

import com.example.monthlyexpensesapp.product.Product;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;


import java.util.Set;

@JsonDeserialize(as = CategoryDto.DeserializeImp.class)
public interface CategoryDto {

    static DeserializeImp deserialize(int id,String name, Set<Product> products) {
        var deserialized = new DeserializeImp(id,name,products);
        return deserialized;
    }

    int getId_category();
    String getCategory_name();
    Set<Product> getProducts();

    class DeserializeImp implements CategoryDto {

        private int id_category;
        private String category_name;
        private Set<Product> products;

        DeserializeImp(final int id_category, final String category_name, final Set<Product> products) {
            this.id_category = id_category;
            this.category_name = category_name;
            this.products = products;
        }

        @Override
        public String toString() {
            return "Category[DTO]{" +
                    "id_category=" + id_category +
                    ", category_name='" + category_name + '\'' +
                    '}';
        }

        @Override
        public int getId_category() {
            return id_category;
        }

        @Override
        public String getCategory_name() {
            return category_name;
        }

        @Override
        public Set<Product> getProducts() {
            return products;
        }
    }
}
