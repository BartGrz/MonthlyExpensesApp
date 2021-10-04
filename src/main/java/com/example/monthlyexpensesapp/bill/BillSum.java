package com.example.monthlyexpensesapp.bill;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bill_sum")
class BillSum {

    @EmbeddedId
    @Getter
    private BillSumPK id;
    @Getter
    @Setter
    private double sum;
    @OneToOne
    @JoinColumn(name = "id_bill")
    @Getter
    @Setter
    private Bill bill;

}
@Embeddable
class BillSumPK implements Serializable {

    @Getter
    private int id_bill;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof BillSumPK)) return false;
        final BillSumPK billSumPK = (BillSumPK) o;
        return id_bill == billSumPK.id_bill;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_bill);
    }
}
