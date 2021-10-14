package com.example.monthlyexpensesapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
class AspectLogic {

    private static final Logger logger = LoggerFactory.getLogger(AspectLogic.class);

    @After("execution (* com.example.monthlyexpensesapp.bill.BillService.toggleBill(..))")
    void logInfoWhenBillIsClosed(JoinPoint jp){
        logger.info(" bill {} added and closed " , jp.getArgs());
    }
   /* @Around("execution (* com.example.monthlyexpensesapp.account.AccountService.updateDebtOfAccounts(..))")
    void logAfterBillSumIsCalculated(JoinPoint jp){
        logger.info("method {} with {}" , jp.getSignature().getName(),jp.getArgs());
    }*/
    @After("execution(* com.example.monthlyexpensesapp.category.CategoryService.updateCategory(..))")
    void logAfterCategoryModification(JoinPoint jp){
        logger.info("Category id={} name has been changed to {}" ,jp.getArgs());
    }
    @After("execution(* com.example.monthlyexpensesapp.account.AccountService.deleteAccount(..))")
    void logAfterDeletingAccount(JoinPoint jp){
        logger.info("Account id={} with name {}  has been deleted" ,jp.getArgs());
    }
   /* @After("execution(* com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFacade.saveNewDebt(..))")
    void logAfterSavingNewDebt(JoinPoint jp){
        logger.info("AccountDebt for account={} with {}  has been saved" ,jp.getArgs());
    }
    @After("execution(* com.example.monthlyexpensesapp.account.accountDebt.AccountDebtFacade.updateDebt(..))")
    void logAfterUpdatingDebt(JoinPoint jp){
        logger.info("AccountDebt with account id={} with {}  has been updated" ,jp.getArgs());
    }*/
}
