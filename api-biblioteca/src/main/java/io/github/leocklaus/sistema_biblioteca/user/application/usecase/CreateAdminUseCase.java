package io.github.leocklaus.sistema_biblioteca.user.application.usecase;

import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;
import io.github.leocklaus.sistema_biblioteca.user.application.command.CreateAdminUserCommand;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateAdminUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateAdminUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(CreateAdminUserCommand command) {

        if (userRepository.existsByEmail(command.email())) {
            throw new DomainException("Já existe usuário com este email");
        }

        String passwordHash = passwordEncoder.encode(command.password());

        User admin = User.createAdmin(
                command.libraryId(),
                command.email(),
                passwordHash
        );

        userRepository.save(admin);

        return admin;
    }
}
