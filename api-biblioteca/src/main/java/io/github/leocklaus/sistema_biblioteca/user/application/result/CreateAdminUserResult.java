package io.github.leocklaus.sistema_biblioteca.user.application.result;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;

import java.util.Set;
import java.util.UUID;

public record CreateAdminUserResult(
        UUID userId,
        Set<Role> roles
) {
}
