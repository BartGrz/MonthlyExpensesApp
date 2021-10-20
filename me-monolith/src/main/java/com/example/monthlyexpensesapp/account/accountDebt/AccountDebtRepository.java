package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AccountDebtRepository extends JpaRepository<AccountDebt, Integer> {


    List<AccountDebt> findAll();

    <S extends AccountDebt> S save(S s);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE ACCOUNT_DEBT set DEBT = :debt where ID_ACCOUNT=:id")
    void updateDebtById_account(@Param("debt") double debt , @Param("id") Integer integer);

    @Query("select ad from AccountDebt ad where ad.id.id_account=:id")
    Optional<AccountDebtDto> findDtoById(@Param("id") Integer integer);

    @Override
    boolean existsById(Integer integer);
}
