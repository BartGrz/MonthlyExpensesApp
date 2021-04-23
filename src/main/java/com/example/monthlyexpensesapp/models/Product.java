package com.example.monthlyexpensesapp.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @NonNull
    private int id_product;
    @NotBlank(message = "field cannot be empty")
    @Getter
    @Setter
    private String product_name;
    @Getter
    @Setter
    private String product_note;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private Category category;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_products_group")
    private ProductsGroup productsGroup;
}
