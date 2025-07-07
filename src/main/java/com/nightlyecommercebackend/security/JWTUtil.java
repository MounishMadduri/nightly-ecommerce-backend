package com.nightlyecommercebackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    // Secret key for signing tokens (keep safe!)
    private final String SECRET_KEY = "F9q3vRZ7nX1q2eVb8yKp9jL0tQs6wE5rA7bD4fH2uJc5rN0yX8vT2wY1sL9pM3kF9q3vRZ7nX1q2eVb8yKp9jL0tQs6wE5rA7bD4fH2uJc5rN0yX8vT2wY1sL9pM3k";

    // Token validity period (5 hours)
    private final long EXPIRATION_TIME = 5 * 60 * 60 * 1000;

    // Generate JWT for given username
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        System.out.println("Extracting username from token: " + token);
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        System.out.println("Extracting expiration from token: " + token);
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract custom claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Validate token against username & expiration
    public boolean validateToken(String token, String username) {
        System.out.println("Validating token for username: " + username+ " with token: " + token);
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if token expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Decode & get claims
    Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
