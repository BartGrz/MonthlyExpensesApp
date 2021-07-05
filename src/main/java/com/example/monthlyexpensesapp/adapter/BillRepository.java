package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends  JpaRepository<Bill, Integer> {


    List<Bill> findAll();


    Bill save(Bill bill);


    Optional<Bill> findById(Integer integer);

    boolean existsById(Integer integer);
}
