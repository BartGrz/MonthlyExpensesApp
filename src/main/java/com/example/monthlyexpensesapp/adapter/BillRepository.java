package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface BillRepository extends  JpaRepository<Bill, Integer> {


    List<Bill> findAll();


    Bill save(Bill bill);


    Optional<Bill> findById(Integer integer);

    boolean existsById(Integer integer);
    @Modifying
    @Transactional
    @Query(value = "delete from Bill b where b.id_bill=:id")
    void deleteById (@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value = "update BillSum bs set bs.sum = :sum where bs.bill.id_bill=:id")
    void sumAllAmongBill(@Param("sum") double sum, @Param("id") int id);
}
