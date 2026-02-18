package io.github.leocklaus.sistema_biblioteca.security.service;

import io.github.leocklaus.sistema_biblioteca.security.jwt.JwtProperties;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class JwtService {
    private final JwtProperties properties;
    private final Key signingKey;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(
                properties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::asString) // ADMIN:uuid
                .toList();

        Date now = new Date();
        Date expiration = new Date(
                now.getTime() + properties.getExpirationMinutes() * 60_000
        );

        return Jwts.builder()
                .setSubject(user.getId().value().toString())
                .claim("email", user.getEmail())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);

        String subject = claims.getSubject();

        if (subject == null || subject.isBlank()) {
            throw new IllegalStateException("JWT sem subject");
        }

        return UUID.fromString(subject);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println("CLAIMS = " + claims);

        return claims;
    }
}
