package com.example.monthlyexpensesapp.bill;

import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.product.Product;
import com.example.monthlyexpensesapp.shop.Shop;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @JoinColumn(name = "id_shop")
    @Getter
    @Setter
    private Shop shop;
    @Getter
    @Setter
    private LocalDate group_date;
    @Getter
    @Setter
    @Column(name = "is_closed")
    private boolean closed;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
    @Getter
    @Setter
    @OneToMany(mappedBy = "bill",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Product> products;

    public Bill() {
        this.closed=false;
    }

    public void updateFrom( Bill source) {
       shop=source.shop;
       group_date=source.group_date;
       closed =source.closed;
       account=source.account;
       products=source.products;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id_bill=" + id_bill +
                ", shop=" + shop.getShop_name() +
                ", group_date=" + group_date +
                ", closed=" + this.closed +
                ", account=" + account.getAccount_name() +
                '}';
    }
}
