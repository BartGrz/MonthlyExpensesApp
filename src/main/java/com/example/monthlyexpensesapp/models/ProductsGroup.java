package com.example.monthlyexpensesapp.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Embedded;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "products_group")
public class ProductsGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Getter
    private int id_products_group;
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JoinColumn(name = "id_shop")
    private Shop shop;
    @Getter
    @Setter
    private LocalDate products_group_date;


}
