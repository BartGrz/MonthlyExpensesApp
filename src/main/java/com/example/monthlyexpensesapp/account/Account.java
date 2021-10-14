package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.bill.Bill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"bills"} )
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id_account;
    @NotBlank(message = "account name cannot be blank")
    @Getter
    @Setter
    private String account_name;
    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Bill> bills;

    public Account() {
    }

    public void updateFrom(Account source) {
        this.account_name = source.account_name;
    }

    @Override
    public String toString() {
        return "{account_name='" + account_name + '\'' +
                '}';
    }

    void assaignId(int id) {
        this.id_account=id;
    }
}
