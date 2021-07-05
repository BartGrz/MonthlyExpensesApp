package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAll();
    Optional<Account> findById(Integer integer);
    Account save(Account entity);
    void delete(Account account);
    boolean existsById(Integer id);
}
