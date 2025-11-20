//package com.shopitto.authservice.util;
//
//public class JwtUtil {
//}
package com.shopitto.authservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "SHOPITTO_SECRET_KEY";  // Move to env later

    // Token validity: 24 hours
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)                // email is used as subject
                .setIssuedAt(new Date())          // token creation time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // signing with secret
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();   // subject = email
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        return extractClaims(token)
                .getExpiration()
                .after(new Date());
    }
}
