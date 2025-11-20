//package com.shopitto.authservice.service.impl;
//
//public class AuthServiceImpl {
//}
package com.shopitto.authservice.service.impl;

import com.shopitto.authservice.dto.AuthResponse;
import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;
import com.shopitto.authservice.entity.User;
//import com.shopitto.authservice.repository.UserRepository;
import com.shopitto.authservice.respository.UserRepository;
import com.shopitto.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return AuthResponse.builder().message("Email already exists").build();
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return AuthResponse.builder().message("Username already exists").build();
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .token(null)  // will add JWT later
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthResponse.builder().message("Invalid username/password").build();
        }

        return AuthResponse.builder()
                .message("Login successful")
                .token(null)  // JWT will be added in next step
                .build();
    }
}
