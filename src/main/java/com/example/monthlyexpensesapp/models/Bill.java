package com.example.monthlyexpensesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Embedded;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "bill")
@JsonIgnoreProperties(value = {"products"} )
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id_bill;
    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "id_shop")
    private Shop shop;
    @Getter
    @Setter
    private LocalDate group_date;
    @Getter
    @Setter
    private boolean is_closed;
    @ManyToOne
    @JoinColumn(name = "id_account")
    @Getter
    @Setter
    private Account account;
    @OneToMany(mappedBy = "bill",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<Product> products;

    public Bill() {
        this.is_closed=false;
    }

    public void updateFrom( Bill source) {
       shop=source.shop;
       group_date=source.group_date;
       is_closed =source.is_closed;
       account=source.account;
       products=source.products;
    }
}
