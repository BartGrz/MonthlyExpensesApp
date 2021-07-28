package com.example.monthlyexpensesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id_product;
    @NotBlank(message = "field cannot be empty")
    @Getter
    @Setter
    private String product_name;
    @Getter
    @Setter
    private double product_price;
    @Getter
    @Setter
    private String product_note;
    @ManyToOne
    @JoinColumn(name = "id_category")
    @Getter
    @Setter
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_bill")
    @Getter
    @Setter
    private Bill bill;
    @ManyToOne
    @JoinColumn(name = "id_account")
    @Getter
    @Setter
    private Account account;
    public void updateFrom(Product source) {

        this.product_name = source.product_name;
        this.product_note = source.product_note;
        this.product_price = source.product_price;
        this.account=source.account;
    }


}
