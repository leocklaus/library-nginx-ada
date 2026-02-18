package io.github.leocklaus.sistema_biblioteca.book.api.dto;

public record RegisterBookRequest(
        String isbn,
        String title,
        String author
) {}
