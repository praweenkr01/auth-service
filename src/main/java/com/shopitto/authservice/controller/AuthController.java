//package com.shopitto.authservice.controller;
//
//public class AuthController {
//}

package com.shopitto.authservice.controller;

import com.shopitto.authservice.dto.AuthResponse;
import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;
import com.shopitto.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

