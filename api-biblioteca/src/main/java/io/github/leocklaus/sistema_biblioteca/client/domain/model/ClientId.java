package io.github.leocklaus.sistema_biblioteca.client.domain.model;

import java.util.Objects;
import java.util.UUID;

public record ClientId(UUID value) {

    public ClientId {
        Objects.requireNonNull(value, "ClientId não pode ser nulo");
    }

    public static ClientId newId() {
        return new ClientId(UUID.randomUUID());
    }
}
