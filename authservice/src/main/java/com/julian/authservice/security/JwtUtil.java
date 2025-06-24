package com.julian.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta para firmar el token
    // private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Duración del token (ejemplo: 1 hora)
    private final long expirationMillis = 3600000;

    // Generar token
    public String generateToken(String subject) {
        Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)             // por ejemplo: email o id del usuario
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)            // firma usando la clave secreta
                .compact();
    }

    // [Más adelante agregaremos métodos como validateToken y getSubjectFromToken]
}
