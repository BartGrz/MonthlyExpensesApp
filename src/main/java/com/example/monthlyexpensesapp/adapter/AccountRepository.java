package com.example.monthlyexpensesapp.adapter;

import com.example.monthlyexpensesapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAll();
    Optional<Account> findById(Integer integer);
    Account save(Account entity);
    void delete(Account account);
    boolean existsById(Integer id);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into ACCOUNT_DEBT (ID_ACCOUNT, DEBT, BALANCE) VALUES (:id_account,0,0)")
    void saveToAccountDebt(@Param("id_account") int id_account);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ACCOUNT_DEBT set BALANCE=:balance where ID_ACCOUNT=:id_account")
    void updateAccountBalance(@Param("balance") double balance, @Param("id_account") int id_account);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ACCOUNT_DEBT set DEBT=:debt where ID_ACCOUNT=:id_account")
    void updateAccountDebt(@Param("debt") double debt, @Param("id_account") int id_account);


}
