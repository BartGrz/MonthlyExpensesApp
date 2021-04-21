package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Account;
import com.example.monthlyexpensesapp.models.AccountRepository;
import com.example.monthlyexpensesapp.models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SqlAccountRepository extends AccountRepository, JpaRepository<Account, Integer> {

    List<Account> findAll();
    Optional<Account> findById(Integer integer);
    @Override
    Account save(Account entity);
    @Override
    void delete(Account account);

    @Override
    boolean existsById(Integer id);
}
