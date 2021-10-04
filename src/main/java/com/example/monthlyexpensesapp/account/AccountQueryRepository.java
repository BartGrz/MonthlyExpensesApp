package com.example.monthlyexpensesapp.account;

import com.example.monthlyexpensesapp.account.dto.AccountDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface AccountQueryRepository extends Repository<Account,Integer> {


    List<AccountDto> findAllDtoBy();
    Optional<AccountDto> findBy(int id_account);

}
