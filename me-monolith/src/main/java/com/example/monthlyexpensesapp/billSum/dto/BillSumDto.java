package com.example.monthlyexpensesapp.billSum.dto;

import com.example.monthlyexpensesapp.bill.Bill;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = BillSumDto.Deserialize.class)
public interface BillSumDto {

     static Deserialize deserialize(int id_bill,double sum, Bill bill){
        return new Deserialize(id_bill,sum,bill);
    }

    int getId_bill();

    double getSum();

    Bill getBill();

     class Deserialize implements BillSumDto {

        private int id_bill;
        private double sum;
        private Bill bill;

        public Deserialize(final int id_bill, final double sum, final Bill bill) {
            this.id_bill = id_bill;
            this.sum = sum;
            this.bill = bill;
        }

        @Override
        public int getId_bill() {
            return id_bill;
        }

        @Override
        public double getSum() {
            return sum;
        }

        @Override
        public Bill getBill() {
            return bill;
        }
    }

}
