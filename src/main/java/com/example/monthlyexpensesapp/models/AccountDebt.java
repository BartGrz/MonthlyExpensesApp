package com.example.monthlyexpensesapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "account_debt")
public class AccountDebt {

    @EmbeddedId
    @Getter
    private AccountDebtPK id;
    @Getter
    @Setter
    private double debt;
    @Getter
    @Setter
    private double balance;
    @OneToOne
    @JoinColumn(name = "id_account")
    @Getter
    @Setter
    private Account account;


}

@Embeddable
class AccountDebtPK implements Serializable {

    @Getter
    private int id_account;
}

