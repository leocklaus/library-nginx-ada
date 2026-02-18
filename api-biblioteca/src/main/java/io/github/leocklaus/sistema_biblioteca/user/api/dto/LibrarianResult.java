package io.github.leocklaus.sistema_biblioteca.user.api.dto;

import java.util.UUID;

public record LibrarianResult(
        UUID id,
        String email
) {}

