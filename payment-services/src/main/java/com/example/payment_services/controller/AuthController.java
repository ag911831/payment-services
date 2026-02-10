package com.example.payment_services.controller;

import com.example.payment_services.dto.LoginRequestDto;
import com.example.payment_services.dto.LoginResponseDto;
import com.example.payment_services.dto.SignUpResponseDto;
import com.example.payment_services.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.ok(authService.signin(loginRequestDto));
    }
}
