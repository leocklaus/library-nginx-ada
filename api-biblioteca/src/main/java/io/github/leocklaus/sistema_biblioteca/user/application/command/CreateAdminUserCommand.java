package io.github.leocklaus.sistema_biblioteca.user.application.command;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

public record CreateAdminUserCommand(
        LibraryId libraryId,
        String email,
        String password
) {
}
