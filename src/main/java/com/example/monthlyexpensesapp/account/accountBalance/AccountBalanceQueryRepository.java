package com.example.monthlyexpensesapp.account.accountBalance;


import com.example.monthlyexpensesapp.account.Account;
import com.example.monthlyexpensesapp.account.accountBalance.dto.AccountBalanceDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountBalanceQueryRepository extends Repository<AccountBalance,Integer> {

    List<AccountBalanceDto> findAll();
    @Query("select ab from Account_Balance ab where ab.id.id_account=:id")
    Optional<AccountBalanceDto> findDtoById(@Param("id") int id);

}
