package com.example.expensetrackerapi.controller;

import com.example.expensetrackerapi.dto.AuthenticationRequest;
import com.example.expensetrackerapi.dto.AuthenticationResponse;
import com.example.expensetrackerapi.dto.AuthorizationRequest;
import com.example.expensetrackerapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping("/authorize")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody AuthorizationRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
