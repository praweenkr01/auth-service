//package com.shopitto.authservice.controller;
//
//public class AuthenticationController {
//}
package com.shopitto.authservice.controller;

import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;
import com.shopitto.authservice.entity.User;
import com.shopitto.authservice.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // ========== REGISTER API ==========
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authenticationService.register(request);
        return ResponseEntity.ok(user);
    }

    // ========== LOGIN API ==========
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authenticationService.login(request);
        return ResponseEntity.ok(token);
    }
}
