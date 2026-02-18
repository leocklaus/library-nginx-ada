package io.github.leocklaus.sistema_biblioteca.book.domain.model;

import io.github.leocklaus.sistema_biblioteca.book.domain.exception.InvalidISBNException;

import java.util.Objects;

public record ISBN(String value) {

    public ISBN {
        Objects.requireNonNull(value, "ISBN não pode ser nulo");

        String normalized = value.replace("-", "").trim();

        if (!normalized.matches("\\d{10}|\\d{13}")) {
            throw new InvalidISBNException(value);
        }
    }
}
