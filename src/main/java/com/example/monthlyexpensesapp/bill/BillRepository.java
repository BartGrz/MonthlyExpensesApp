package com.example.monthlyexpensesapp.bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface BillRepository extends  JpaRepository<Bill, Integer> {





    Bill save(Bill bill);


    Optional<Bill> findById(Integer integer);

    boolean existsById(Integer integer);
    @Modifying
    @Transactional
    @Query(value = "delete from Bill b where b.id_bill=:id")
    void deleteById (@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "Update BILL_SUM set SUM=:sum where ID_BILL=:id")
    void sumAllAmongBill(@Param("sum") double sum, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "delete from BillSum where bill.id_bill=:id")
    void deleteFromBillSumBillById (@Param("id")int id);
    @Transactional
    @Modifying
    @Query( nativeQuery = true, value = "insert into BILL_SUM (ID_BILL, SUM) VALUES (:id_bill, :sum );")
    void saveBilltoBillSum(@Param("id_bill") int id_bill, @Param("sum") double sum);

    @Query("select bs.sum from BillSum bs where bs.id.id_bill=:id")
    double getBillSumById(@Param("id") int id);
}
