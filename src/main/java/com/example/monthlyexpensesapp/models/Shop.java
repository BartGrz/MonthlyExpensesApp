package com.example.monthlyexpensesapp.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Getter
    @NotNull
    private int id_shop;
    @Getter
    @Setter
    @NotBlank(message = "shop name cannot be blank")
    private String shop_name;

    public void updateFrom(Shop source) {

        shop_name =source.shop_name;

    }

}
