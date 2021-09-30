package com.example.monthlyexpensesapp.account;

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
    @OneToOne
    @JoinColumn(name = "id_account" ,updatable = false, insertable = false)
    @Getter
    @Setter
    private Account account;
    @Getter
    @Setter
    private double debt;
}

@Embeddable
class AccountDebtPK implements Serializable {

    @Getter
    private int id_account;
    @Getter
    private int id_account_owed;

}

