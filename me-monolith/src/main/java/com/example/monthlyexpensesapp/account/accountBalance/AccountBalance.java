package com.example.monthlyexpensesapp.account.accountBalance;

import com.example.monthlyexpensesapp.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table
@Entity(name = "Account_Balance")
public class AccountBalance {

    @EmbeddedId
    @Getter
    @Setter
    private AccountBalancePK id;
    @ManyToOne
    @JoinColumn(name = "id_account" , insertable = false ,updatable = false)
    @Getter
    @Setter
    private Account account;
    @Getter
    @Setter
    private double balance;
    @Override
    public String toString() {
        return "AccountBalance{" +
                "id=" + id +
                ", account=" + account +", "+ balance+
                '}';
    }
}
@Embeddable
class AccountBalancePK implements Serializable {
    @Getter
    @Setter
    private int id_account ;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountBalancePK)) return false;
        final AccountBalancePK that = (AccountBalancePK) o;
        return id_account == that.id_account ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_account);
    }

    @Override
    public String toString() {
        return "AccountBalancePK{" +
                "id_account=" + id_account +
                '}';
    }
}
