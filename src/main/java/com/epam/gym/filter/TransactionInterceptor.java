package com.epam.gym.filter;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class TransactionInterceptor implements HandlerInterceptor {
    public static final String TX_ID = "txId";
    public static final String HEADER = "X-Transaction-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tx = request.getHeader(HEADER);
        if (tx == null || tx.isEmpty()) tx = UUID.randomUUID().toString();
        MDC.put(TX_ID, tx);
        response.setHeader(HEADER, tx);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(TX_ID);
    }
}