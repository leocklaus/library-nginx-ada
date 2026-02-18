package io.github.leocklaus.sistema_biblioteca.book.domain.exception;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;

import java.util.UUID;

public class DuplicateISBNException extends RuntimeException {
    public DuplicateISBNException(UUID libraryId, ISBN isbn) {
        super("O ISBN: " + isbn + " já existe nesta biblioteca");
    }
}
