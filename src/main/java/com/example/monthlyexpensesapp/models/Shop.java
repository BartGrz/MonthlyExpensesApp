package com.example.monthlyexpensesapp.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "shop")
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Getter
    private int id_shop;
    @Getter
    @Setter
    @NotBlank(message = "shop name cannot be blank")
    private String shop_name;
  // @OneToMany(cascade = CascadeType.ALL,mappedBy = "shop")


    public void updateFrom(Shop source) {

        shop_name =source.shop_name;

    }

}
