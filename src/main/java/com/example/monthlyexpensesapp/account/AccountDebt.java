package com.example.monthlyexpensesapp.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDebtPK)) return false;
        final AccountDebtPK that = (AccountDebtPK) o;
        return id_account == that.id_account && id_account_owed == that.id_account_owed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_account, id_account_owed);
    }
}

