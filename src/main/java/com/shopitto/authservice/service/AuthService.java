package com.shopitto.authservice.service;

//public interface AuthService {
//}
//package com.shopitto.authservice.service;

import com.shopitto.authservice.dto.AuthResponse;
import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
