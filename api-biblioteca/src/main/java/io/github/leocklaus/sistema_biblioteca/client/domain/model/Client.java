package io.github.leocklaus.sistema_biblioteca.client.domain.model;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

public class Client {
    private final ClientId id;
    private final LibraryId libraryId;
    private String name;
    private String email;

    private Client(
            ClientId id,
            LibraryId libraryId,
            String name,
            String email
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.name = name;
        this.email = email;
    }

    public static Client create(
            LibraryId libraryId,
            String name,
            String email
    ) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Nome do cliente é obrigatório");
        }

        if (email == null || email.isBlank()) {
            throw new DomainException("Email do cliente é obrigatório");
        }

        return new Client(
                ClientId.newId(),
                libraryId,
                name.trim(),
                email.trim().toLowerCase()
        );
    }

    public static Client rehydrate(
            ClientId id,
            LibraryId libraryId,
            String name,
            String email
    ) {
        return new Client(
                id,
                libraryId,
                name,
                email
        );
    }

    public ClientId getId() {
        return id;
    }

    public LibraryId getLibraryId() {
        return libraryId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
