package io.github.leocklaus.sistema_biblioteca.security.service;

import io.github.leocklaus.sistema_biblioteca.security.exception.InvalidCredentialsException;
import io.github.leocklaus.sistema_biblioteca.security.jwt.TokenService;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return tokenService.generateToken(user);
    }
}
