package com.example.monthlyexpensesapp.shop;

import com.example.monthlyexpensesapp.bill.Bill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@JsonIgnoreProperties(value = {"bills"})
public class Shop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id_shop;
    @Getter
    @Setter
    @NotBlank(message = "shop name cannot be blank")
    private String shop_name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop",fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Bill> bills;
    public void updateFrom(Shop source) {
        
        shop_name = source.shop_name;
        
    }
    
}
