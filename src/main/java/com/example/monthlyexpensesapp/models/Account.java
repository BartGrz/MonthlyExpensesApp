package com.example.monthlyexpensesapp.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import lombok.Setter;
import javax.persistence.*;



@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @NotNull
    private int id_account;
    @NotNull
    @NotBlank(message = "account name cannot be blank")
    @Getter@Setter
    private String account_name;


    public void updateFrom(Account source) {
        account_name = source.account_name;
    }

}
