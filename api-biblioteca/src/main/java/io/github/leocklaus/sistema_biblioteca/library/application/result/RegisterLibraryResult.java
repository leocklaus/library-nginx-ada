package io.github.leocklaus.sistema_biblioteca.library.application.result;

import java.util.UUID;

public record RegisterLibraryResult(
        UUID libraryId,
        UUID adminUserId,
        String token
) {}
