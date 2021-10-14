package com.example.monthlyexpensesapp.account.accountBalance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance,Integer> {

    @Override
    <S extends AccountBalance> S save(S s);

    @Override
    Optional<AccountBalance> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
