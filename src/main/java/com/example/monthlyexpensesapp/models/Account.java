package com.example.monthlyexpensesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "account")
@JsonIgnoreProperties(value = {"bills"} )
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @NotNull
    private int id_account;
    @NotBlank(message = "account name cannot be blank")
    @Getter
    @Setter
    private String account_name;
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    @Setter
    @Getter
    private Set<Bill> bills;

    public Account() {
    }

    public void updateFrom(Account source) {
        account_name = source.account_name;
    }

}
