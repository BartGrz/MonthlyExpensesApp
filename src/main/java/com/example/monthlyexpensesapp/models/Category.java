package com.example.monthlyexpensesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({"products"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id_category;
    @NotNull
    @NotBlank(message = " category name cannot be empty")
    @Getter
    @Setter
    private String category_name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @Getter
    @Setter
    private Set<Product> products;


    public void updateFrom(Category source) {
        category_name = source.category_name;
    }

}
