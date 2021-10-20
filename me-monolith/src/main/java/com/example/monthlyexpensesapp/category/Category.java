package com.example.monthlyexpensesapp.category;

import com.example.monthlyexpensesapp.product.Product;
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

    @Override
    public String toString() {
        return "{category_name='" + category_name + '\'' +
                '}';
    }

    public void updateFrom(Category source) {
        category_name = source.category_name;
    }

    void assaignId(int id) {
        this.id_category=id;
    }


}
