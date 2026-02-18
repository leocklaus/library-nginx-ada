package io.github.leocklaus.sistema_biblioteca.client.api.dto;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;

import java.util.UUID;

public record ClientResponse(
        UUID clientId,
        String name,
        String email
) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(
                client.getId().value(),
                client.getName(),
                client.getEmail()
        );
    }
}
