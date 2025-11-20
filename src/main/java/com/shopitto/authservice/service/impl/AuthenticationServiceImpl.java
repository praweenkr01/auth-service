//package com.shopitto.authservice.service.impl;
//
//public class AuthenticationServiceImpl {
//}
package com.shopitto.authservice.service.impl;

import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;
import com.shopitto.authservice.entity.User;
//import com.shopitto.authservice.repository.UserRepository;
import com.shopitto.authservice.respository.UserRepository;
import com.shopitto.authservice.service.AuthenticationService;
import com.shopitto.authservice.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatch) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
