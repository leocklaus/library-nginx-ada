package io.github.leocklaus.sistema_biblioteca.user.domain.exception;

import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.util.UUID;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(UUID id) {
        super("Usuário não encontrado: " + id);
    }
}
