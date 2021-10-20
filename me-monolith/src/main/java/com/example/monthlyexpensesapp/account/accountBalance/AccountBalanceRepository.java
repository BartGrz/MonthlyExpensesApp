package com.example.monthlyexpensesapp.account.accountBalance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance,Integer> {

    @Override
    <S extends AccountBalance> S save(S s);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "UPDATE ACCOUNT_BALANCE set BALANCE=BALANCE+:balance where ID_ACCOUNT=:id")
    void updateBalanceBy(@Param("id") int id, @Param("balance")double debt);

    @Override
    Optional<AccountBalance> findById(Integer integer);

    @Override
    void deleteById(Integer integer);
}
