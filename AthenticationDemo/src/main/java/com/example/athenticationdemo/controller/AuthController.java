package com.example.athenticationdemo.controller;

import com.example.athenticationdemo.dto.LoginRequestDto;
import com.example.athenticationdemo.dto.LoginResponseDto;
import com.example.athenticationdemo.dto.RegisterRequestDto;
import com.example.athenticationdemo.jwt.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {

        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDto> authenticate(
            @RequestBody LoginRequestDto request
    ) throws Exception {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        try {
            service.refreshToken(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

