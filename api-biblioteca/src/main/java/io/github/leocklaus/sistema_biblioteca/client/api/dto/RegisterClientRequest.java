package io.github.leocklaus.sistema_biblioteca.client.api.dto;

public record RegisterClientRequest(
        String name,
        String email
) {
}
