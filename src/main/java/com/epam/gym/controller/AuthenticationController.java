package com.epam.gym.controller;

import com.epam.gym.dto.LoginReq;
import com.epam.gym.dto.ChangeLoginReq;
import com.epam.gym.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authService;
    private final RequestMetrics requestMetrics;

    private String tx() { return MDC.get("transactionId"); }

    @GetMapping("/login")
    public ResponseEntity<Void> login(@Valid @ModelAttribute LoginReq request) {
        log.info("[{}] GET /api/login {}", tx(), req);
        authService.authenticate(request.getUsername(), request.getPassword());
        // 💡 increment our Prometheus counter
        requestMetrics.incrementLogin();
        log.info("[{}] RESP 200 login successful", tx());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/login")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody ChangeLoginReq request) {
        authService.changeLogin(request.getOldUsername(), request.getNewUsername());
        return ResponseEntity.ok().build();
    }
}