package com.example.monthlyexpensesapp.billSum;

import com.example.monthlyexpensesapp.bill.Bill;
import com.example.monthlyexpensesapp.bill.BillQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillSumFacade {
    public static final Logger logger = LoggerFactory.getLogger(BillSumFacade.class);

    private final BillSumQueryRepository billSumQueryRepository;
    private final BillSumRepository billSumRepository;
    private final BillQueryRepository billQueryRepository;

    BillSumFacade(final BillSumQueryRepository billSumQueryRepository, final BillSumRepository billSumRepository, final BillQueryRepository billQueryRepository) {
        this.billSumQueryRepository = billSumQueryRepository;
        this.billSumRepository = billSumRepository;
        this.billQueryRepository = billQueryRepository;
    }

    public void saveNewBillSum(Bill bill) {
        var toBeSaved = new BillSum();
        var pk = new BillSumPK();
        pk.setId_bill(bill.getId_bill());
        toBeSaved.setBill(bill);
        toBeSaved.setId(pk);
            double sum = bill.getProducts()
                    .stream()
                    .mapToDouble(value -> value.getProduct_price()).sum();
            toBeSaved.setSum(sum);
         billSumRepository.saveNewBillSum(bill.getId_bill(),toBeSaved.getSum());
    }

    public void deleteBillSum(int id_bill) {
        if (!billSumQueryRepository.existsById(id_bill)) {
            throw new IllegalArgumentException("Bill with id : " + id_bill + " does not exist");
        }
        billSumRepository.deleteById(id_bill);
    }

    public void updateBillSum(Bill bill) {
        var pk = new BillSumPK();
        pk.setId_bill(bill.getId_bill());
        if (!billSumQueryRepository.existsById(bill.getId_bill())) {
            throw new IllegalArgumentException("Bill with id : " + bill.getId_bill() + " does not exist");
        }
        saveNewBillSum(bill);
    }

    public double getSumBy(Bill bill) {
        if (!billSumQueryRepository.existsById(bill.getId_bill())) {
            throw new IllegalArgumentException("Bill with id : " + bill.getId_bill() + " does not exist");
        }else {
            return billSumQueryRepository.findById(bill.getId_bill()).get().getSum();
        }
    }
}
