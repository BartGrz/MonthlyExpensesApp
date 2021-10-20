package com.example.monthlyexpensesapp.billSum;

import com.example.monthlyexpensesapp.bill.Bill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bill_sum")
@ToString
class BillSum {

    @EmbeddedId
    @Getter
    @Setter
    private BillSumPK id;
    @Getter
    @Setter
    private double sum;
    @ManyToOne
    @JoinColumn(name = "id_bill", insertable = false, updatable = false)
    @Getter
    @Setter
    private Bill bill;

}

@Embeddable
@ToString
class BillSumPK implements Serializable {

    @Getter
    @Setter
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
