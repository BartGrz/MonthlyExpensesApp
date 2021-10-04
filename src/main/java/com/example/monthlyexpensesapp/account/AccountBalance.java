package com.example.monthlyexpensesapp.account;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalancePK)) return false;
        final AccountBalancePK that = (AccountBalancePK) o;
        return id_account == that.id_account && Double.compare(that.balance, balance) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_account, balance);
    }
}
