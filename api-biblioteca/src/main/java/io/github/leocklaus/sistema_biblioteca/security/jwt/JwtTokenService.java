package io.github.leocklaus.sistema_biblioteca.security.jwt;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements TokenService{
    private final JwtTokenProvider provider;

    public JwtTokenService(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public String generateToken(User user) {
        return provider.generateToken(user);
    }
}
