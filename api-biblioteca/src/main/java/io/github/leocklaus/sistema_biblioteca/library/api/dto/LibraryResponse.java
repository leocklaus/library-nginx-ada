package io.github.leocklaus.sistema_biblioteca.library.api.dto;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;

import java.util.UUID;

public record LibraryResponse(
        UUID id,
        String name,
        String email,
        String status
) {

    public static LibraryResponse from(Library library) {
        return new LibraryResponse(
                library.getId().value(),
                library.getName(),
                library.getEmail(),
                library.getStatus().name()
        );
    }
}
