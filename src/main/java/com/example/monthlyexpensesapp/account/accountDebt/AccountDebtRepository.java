package com.example.monthlyexpensesapp.account.accountDebt;

import com.example.monthlyexpensesapp.account.accountDebt.dto.AccountDebtDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountDebtRepository extends JpaRepository<AccountDebt, Integer> {


    List<AccountDebt> findAll();

    <S extends AccountDebtDto> S save(S s);

    @Query("select ad from AccountDebt ad where ad.id.id_account=:id")
    Optional<AccountDebtDto> findDtoById(@Param("id") Integer integer);

    @Override
    boolean existsById(Integer integer);
}
