package com.document.documentauth.Services;

import com.document.documentauth.Domain.Entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${jwtSignKey}")
    private String JWTSIGNKEY;  // The key for signing JWT tokens

    /**
     * Generate a JWT token for a user
     *
     * @param userDetails User data
     * @return JWT token
     */
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("login", userDetails.getLogin());  // Using 'login' instead of 'username', as per your User class

        return generateToken(claims, userDetails.getLogin());
    }

    /**
     * Generate JWT token with additional claims
     *
     * @param extraClaims Additional data to include in the token
     * @param username    The username (login)
     * @return JWT token
     */
    private String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)  // The subject is the username (login)
                .setIssuedAt(new Date())  // Set the issued date to the current date
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))  // Token expires in 24 hours
                .signWith(getSigningKey(), io.jsonwebtoken.SignatureAlgorithm.HS256)  // Sign the token using HS256 algorithm
                .compact();
    }


    /**
     * Get the signing key for token signing
     *
     * @return The signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWTSIGNKEY);  // Decode the signing key from base64
        return Keys.hmacShaKeyFor(keyBytes);  // Create a signing key for HS256 algorithm
    }
}
