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

    private String tx() { return MDC.get("transactionId"); }

    @GetMapping("/login")
    public ResponseEntity<Void> login(@Valid @ModelAttribute LoginReq request) {
        authService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/login")
    public ResponseEntity<Void> changeLogin(@Valid @RequestBody ChangeLoginReq request) {
        authService.changeLogin(request.getOldUsername(), request.getNewUsername());
        return ResponseEntity.ok().build();
    }
}