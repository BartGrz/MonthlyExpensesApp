package com.example.monthlyexpensesapp.billSum;

import com.example.monthlyexpensesapp.billSum.dto.BillSumDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Transient;
import javax.transaction.Transactional;

interface BillSumRepository extends Repository<BillSum,Integer> {

    /*@Override
    <S extends BillSum> S save(S s);*/
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "INSERT INTO BILL_SUM (id_bill, sum) VALUES (:id,:sum)")
    void saveNewBillSum(@Param("id")int id, @Param("sum") double sum);
   // BillSum save (BillSum billSum);
    void deleteById(Integer integer);
}
