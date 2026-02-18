package io.github.leocklaus.sistema_biblioteca.client.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.client.infrastructure.persistence.ClientEntity;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

public class ClientMapper {
    private ClientMapper() {}

    public static ClientEntity toEntity(Client client) {
        return new ClientEntity(
                client.getId().value(),
                client.getLibraryId().value(),
                client.getName(),
                client.getEmail()
        );
    }

    public static Client toDomain(ClientEntity entity) {
        return Client.rehydrate(
                new ClientId(entity.getId()),
                new LibraryId(entity.getLibraryId()),
                entity.getName(),
                entity.getEmail()
        );
    }
}
