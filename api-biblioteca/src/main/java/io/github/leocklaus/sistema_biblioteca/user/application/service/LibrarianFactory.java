package io.github.leocklaus.sistema_biblioteca.user.application.service;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LibrarianFactory {

    private final PasswordEncoder passwordEncoder;

    public LibrarianFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(String email, String rawPassword, UUID libraryId) {
        return User.createLibrarian(
                new LibraryId(libraryId),
                email,
                passwordEncoder.encode(rawPassword)
        );
    }
}
