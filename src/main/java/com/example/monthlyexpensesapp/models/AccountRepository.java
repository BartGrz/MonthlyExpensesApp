package com.example.monthlyexpensesapp.models;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAll();
    Account save(Account entity);
    Optional<Account> findById(Integer integer);
    void delete(Account account);
    boolean existsById(Integer id);

}
