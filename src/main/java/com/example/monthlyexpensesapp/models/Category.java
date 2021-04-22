package com.example.monthlyexpensesapp.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @NotNull
    private int id_category;
    @NotNull
    @NotBlank(message = " category name cannot be empty")
    @Getter@Setter
    private String category_name;

    public void updateFrom(Category source) {
        category_name = source.category_name;
    }

}
