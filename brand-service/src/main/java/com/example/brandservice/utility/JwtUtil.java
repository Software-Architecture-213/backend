package com.example.brandservice.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    // Generate a secure 256-bit key for HS256 using the Keys class
    private Key getSecretKey() {
        if (secretKeyString != null && !secretKeyString.isEmpty()) {
            return Keys.hmacShaKeyFor(secretKeyString.getBytes()); // Use the provided secret key, converted to bytes
        } else {
            return Keys.secretKeyFor(SignatureAlgorithm.HS256); // Fallback to securely generated key if not provided
        }
    }

    // Generate a JWT token using HS256
    public String generateToken(String subject) {
        try {
            Key secretKey = getSecretKey();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                    .signWith(secretKey, SignatureAlgorithm.HS256) // Using the secure 256-bit key for signing
                    .compact();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error appropriately
            return null;
        }
    }

    // Generate a Refresh Token (long-lived)
    public String generateRefreshToken(String subject) {
        try {
            Key secretKey = getSecretKey();
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // Refresh token expires in 30 days
                    .signWith(secretKey, SignatureAlgorithm.HS256) // Use the secure 256-bit key for signing
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to extract subject from the token
    public String extractSubject(String token) {
        try {
            Key secretKey = getSecretKey();
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Use the secret key to validate the token
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Token is invalid or expired
        }
    }

}
