package com.example.monthlyexpensesapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bill_sum")
public class BillSum {

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

}
