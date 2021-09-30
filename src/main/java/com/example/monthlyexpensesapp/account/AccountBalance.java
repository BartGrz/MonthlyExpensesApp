package com.example.monthlyexpensesapp.account;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity(name = "Account_Balance")
public class AccountBalance {

    @EmbeddedId
    private AccountBalancePK id;

    @ManyToOne
    @JoinColumn(name = "id_account" , insertable = false ,updatable = false)
    private Account account;

}
@Embeddable
class AccountBalancePK implements Serializable {
    @Getter
    private int id_account ;
    @Getter
    private double balance;


}
