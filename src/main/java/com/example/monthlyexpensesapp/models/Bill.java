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
    @ManyToOne
    @JoinColumn(name = "id_account")
    @Getter
    @Setter
    private Account account;
    @OneToMany(mappedBy = "bill",fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<Product> products;



}
