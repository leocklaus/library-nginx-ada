package io.github.leocklaus.sistema_biblioteca.library.application.command;

public record RegisterLibraryCommand(
        String libraryName,
        String libraryEmail,
        String adminEmail,
        String adminPassword
) {
}
