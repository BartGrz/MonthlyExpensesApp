package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.account.dto.AccountDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface AccountQueryRepository extends Repository<Account, Integer> {


    List<AccountDto> findAllDtoBy();

    /*cannot force spring to create query base on projection,
    substitute method with @query anno */
    @Query("select a from Account a where a.id_account=:id")
    Optional<AccountDto> findDtoById(@Param("id") Integer id);

}
