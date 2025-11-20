//package com.shopitto.authservice.security;
//
//public class JwtAuthenticationFilter {
//}
//package com.shopitto.authservice.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        String username = null;
//        String token = null;
//
//        // Check header format: "Bearer <token>"
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7);
//            if (jwtUtil.isTokenValid(token)) {
//                username = jwtUtil.extractUsername(token);
//            }
//        }
//
//        // If username found and user is not already authenticated
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.isTokenValid(token)) {
//
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // Set authentication in security context
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
package com.shopitto.authservice.security;

import com.shopitto.authservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception ignored) {
                // invalid token → ignore → will result in unauthorized
            }
        }

        // If email is valid & user not authenticated yet
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtUtil.isTokenValid(token)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Set authentication into spring context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
