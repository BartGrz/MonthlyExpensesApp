package com.example.monthlyexpensesapp.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    /* List<Account> findAll();
     Optional<Account> findById(Integer integer);*/
    Account save(Account entity);

    void delete(Account account);

    boolean existsById(Integer id);

    @Query("select ac from Account ac where ac.account_name=:name")
    Optional<Account> findByName(@Param("name") String name);

    @Override
    void deleteById(Integer integer);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert into ACCOUNT_DEBT (ID_ACCOUNT, DEBT,ID_ACCOUNT_OWED) VALUES (:id_account,:debt,:id_account_owed)")
    void saveToAccountDebt(@Param("id_account") int id_account, @Param("id_account_owed") int id_account_owed, @Param("debt") double debt);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ACCOUNT_BALANCE set BALANCE=:balance where ID_ACCOUNT=:id_account")
    void updateAccountBalance(@Param("balance") double balance, @Param("id_account") int id_account);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE ACCOUNT_DEBT set DEBT=:debt where ID_ACCOUNT=:id_account AND ID_ACCOUNT_OWED=:id_account_owed")
    void updateAccountDebt(@Param("debt") double debt, @Param("id_account_owed") int id_account_owed, @Param("id_account") int id_account);

    @Query("select ac.debt from AccountDebt ac where ac.id.id_account=:id AND ac.id.id_account_owed=:id_account_owed")
    double getAccountDebtById(@Param("id") int id, @Param("id_account_owed") int id_account_owed);

    @Query(nativeQuery = true, value = "select count(*) =0 from ACCOUNT_DEBT where ID_ACCOUNT=:id_account AND ID_ACCOUNT_OWED=:id_account_owed")
    boolean existsById_debtor(@Param("id_account") int id_account, @Param("id_account_owed") int id_account_owed);

    @Query("select ab from AccountDebt ab where ab.account.id_account=:id")
    double getBalanceById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from ACCOUNT_DEBT where ID_ACCOUNT_OWED=:id_account")
    void deleteAccountDebtById(@Param("id_account") int id_account);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from ACCOUNT_DEBT where ID_ACCOUNT=:id_account")
    void deleteWholeDebt(@Param("id_account") int id_account);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from BILL where ID_ACCOUNT=:id_account")
    void deleteFromBillHistory(@Param("id_account") int id_account);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from PRODUCT where ID_ACCOUNT=:id_account")
    void deleteFromProductHistory(@Param("id_account") int id_account);

}
