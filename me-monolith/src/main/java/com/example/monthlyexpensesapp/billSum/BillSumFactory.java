package com.example.monthlyexpensesapp.billSum;

import com.example.monthlyexpensesapp.billSum.dto.BillSumDto;

class BillSumFactory {

    static BillSum from(BillSumDto source) {
        var billSum = new BillSum();
        var pk = new BillSumPK();
        pk.setId_bill(source.getId_bill());
        billSum.setId(pk);
        billSum.setBill(source.getBill());
        billSum.setSum(source.getSum());
        return billSum;
    }

}
