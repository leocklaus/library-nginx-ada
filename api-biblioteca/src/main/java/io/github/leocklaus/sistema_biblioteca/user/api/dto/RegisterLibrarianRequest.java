package io.github.leocklaus.sistema_biblioteca.user.api.dto;

public record RegisterLibrarianRequest(
        String email,
        String password
) {}
