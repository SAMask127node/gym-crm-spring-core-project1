package com.epam.gym.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class TransactionalLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(TransactionalLoggingAspect.class);

    @Pointcut("@annotation(transactional)")
    public void txMethods(Transactional transactional) {}

    @Around("txMethods(transactional)")
    public Object around(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {
        String tx = MDC.get(com.epam.gym.filter.TransactionInterceptor.TX_ID);
        String sig = pjp.getSignature().toShortString();
        log.info("[{}] BEGIN {}", tx, sig);
        try {
            Object result = pjp.proceed();
            log.info("[{}] COMMIT {}", tx, sig);
            return result;
        } catch (Throwable ex) {
            log.error("[{}] ROLLBACK {} => {}", tx, sig, ex.getMessage());
            throw ex;
        }
    }
}