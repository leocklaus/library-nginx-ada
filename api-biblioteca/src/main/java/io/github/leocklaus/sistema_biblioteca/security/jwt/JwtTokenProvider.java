package io.github.leocklaus.sistema_biblioteca.security.jwt;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

    private final Key key;
    private final long expirationMillis;

    public JwtTokenProvider(JwtProperties properties) {
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
        this.expirationMillis = properties.getExpirationMinutes() * 60_000;
    }

    public String generateToken(User user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(user.getId().value().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream().map(Role::asString).toList())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        }
}
