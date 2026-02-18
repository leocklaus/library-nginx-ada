package io.github.leocklaus.sistema_biblioteca.security.jwt;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;

public interface TokenService {
    String generateToken(User user);
}
