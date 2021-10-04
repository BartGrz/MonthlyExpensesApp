package com.example.monthlyexpensesapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
class AspectLogic {

    private static final Logger logger = LoggerFactory.getLogger(AspectLogic.class);

    @After("execution (* com.example.monthlyexpensesapp.bill.BillService.toggleBill(..))")
    void logInfoWhenBillIsClosed(JoinPoint jp){
        logger.info(" bill {} added and closed " , jp.getArgs());
    }
    @After("execution (* com.example.monthlyexpensesapp.account.AccountService.updateDebtOfAccounts(..))")
    void logAfterBillSumIsCalculated(JoinPoint jp){
        logger.info("method {} with {}" , jp.getSignature().getName(),jp.getArgs());
    }
}
