//package com.shopitto.authservice.service;
//
//public interface AuthenticationService {
//}
package com.shopitto.authservice.service;

import com.shopitto.authservice.dto.LoginRequest;
import com.shopitto.authservice.dto.RegisterRequest;
import com.shopitto.authservice.entity.User;

public interface AuthenticationService {
    User register(RegisterRequest request);
    String login(LoginRequest request);
}
