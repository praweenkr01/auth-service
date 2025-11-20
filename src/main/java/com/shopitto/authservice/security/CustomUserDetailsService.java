//package com.shopitto.authservice.security;
//
//public class CustomUserDetailsService {
//}
//package com.shopitto.authservice.security;
//
//import com.shopitto.authservice.entity.User;
////import com.shopitto.authservice.repository.UserRepository;
//
//import com.shopitto.authservice.respository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRole())   // Spring will convert "ADMIN" → ROLE_ADMIN
//                .build();
//    }
//}
package com.shopitto.authservice.security;

import com.shopitto.authservice.entity.User;
//import com.shopitto.authservice.repository.UserRepository;
import com.shopitto.authservice.respository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new CustomUserDetails(user);
    }
}
