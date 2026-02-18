package io.github.leocklaus.sistema_biblioteca.library.domain.exception;

import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.util.UUID;

public class LibraryNotFoundException extends DomainException {

    public LibraryNotFoundException(UUID id) {
        super("Biblioteca não encontrada: " + id);
    }
}
