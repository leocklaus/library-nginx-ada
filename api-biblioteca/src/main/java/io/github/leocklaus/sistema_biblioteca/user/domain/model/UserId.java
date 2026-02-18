package io.github.leocklaus.sistema_biblioteca.user.domain.model;

import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        if (value == null) {
            throw new DomainException("UserId não pode ser nulo");
        }
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}
