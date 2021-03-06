package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "account_debt")
@ToString
public class AccountDebt {

    @EmbeddedId
    @Getter
    @Setter
    private AccountDebtPK id;
    @ManyToOne
    @JoinColumn(name = "id_account" ,updatable = false, insertable = false)
    @Getter
    @Setter
    private Account account;
    @Getter
    @Setter
    private double debt;
    @Getter
    @Setter
    private int id_account_owed;
}

@Embeddable
@ToString
class AccountDebtPK implements Serializable {

    @Getter
    @Setter
    private int id_account;


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDebtPK)) return false;
        final AccountDebtPK that = (AccountDebtPK) o;
        return id_account == that.id_account ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_account);
    }
}

