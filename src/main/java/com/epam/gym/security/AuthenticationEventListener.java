// src/main/java/com/epam/gym/security/AuthenticationEventListener.java
package com.epam.gym.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    private final BruteForceService bruteForceService;

    public AuthenticationEventListener(BruteForceService bruteForceService) {
        this.bruteForceService = bruteForceService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        bruteForceService.loginFailed(username);
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        bruteForceService.loginSucceeded(username);
    }
}
