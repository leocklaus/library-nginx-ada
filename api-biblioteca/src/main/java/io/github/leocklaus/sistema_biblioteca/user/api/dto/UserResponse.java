package io.github.leocklaus.sistema_biblioteca.user.api.dto;

import io.github.leocklaus.sistema_biblioteca.user.domain.model.Role;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserResponse(
        UUID id,
        String email,
        Set<String> roles
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId().value(),
                user.getEmail(),
                user.rolesSnapshot()
                        .stream()
                        .map(Role::asString)
                        .collect(Collectors.toSet())
        );
    }
}
