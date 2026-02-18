package io.github.leocklaus.sistema_biblioteca.book.domain.model;

import java.util.UUID;

public record BookId(UUID value) {

    public static BookId newId() {
        return new BookId(UUID.randomUUID());
    }
}
