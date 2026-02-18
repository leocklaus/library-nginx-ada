package io.github.leocklaus.sistema_biblioteca.book.api.dto;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String author,
        String isbn
) {

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId().value(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn().toString()
        );
    }
}
