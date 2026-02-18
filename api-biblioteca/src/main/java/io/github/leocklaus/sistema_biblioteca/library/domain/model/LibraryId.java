package io.github.leocklaus.sistema_biblioteca.library.domain.model;

import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.util.Objects;
import java.util.UUID;

public record LibraryId(UUID value) {
    public LibraryId {
        if(value == null){
            throw new DomainException("LibraryId não pode ser nulo");
        }
    }

    public static LibraryId newId() {
        return new LibraryId(UUID.randomUUID());
    }
}
