package com.example.monthlyexpensesapp.bill;


import com.example.monthlyexpensesapp.bill.dto.BillDto;
import org.springframework.data.repository.Repository;


import java.util.List;
import java.util.Optional;

public interface BillQueryRepository  extends Repository<Bill, Integer> {

    List<BillDto> findAllBy();
    Optional<BillDto> findById(int id);
    List<Bill> findAll();

}
