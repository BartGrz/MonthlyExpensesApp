package com.example.monthlyexpensesapp.billSum;


import com.example.monthlyexpensesapp.billSum.dto.BillSumDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface BillSumQueryRepository extends Repository<BillSum,Integer> {

    List<BillSumDto> findAll();
    @Query("select bs from BillSum bs where bs.id.id_bill=:id")
    Optional<BillSumDto> findById(@Param("id")int id);

    @Query(nativeQuery = true,value = "SELECT count(*) >0 from BILL_SUM where ID_BILL=:id")
    boolean existsById(@Param("id")long id);



}
