package io.github.leocklaus.sistema_biblioteca.library.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Library {
    private final LibraryId id;
    private String name;
    private String email;
    private LibraryStatus status;
    private final Instant createdAt;

    private Library(
            LibraryId id,
            String name,
            String email,
            LibraryStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Library create(String name, String email) {
        validate(name, email);

        return new Library(
                LibraryId.newId(),
                name,
                email,
                LibraryStatus.ACTIVE,
                Instant.now()
        );
    }

    public static Library rehydrate(
            LibraryId id,
            String name,
            String email,
            LibraryStatus status,
            Instant createdAt
    ) {
        return new Library(
                id,
                name,
                email,
                status,
                createdAt
        );
    }

    private static void validate(String name, String email) {
        Objects.requireNonNull(name, "Nome da biblioteca é obrigatório");
        Objects.requireNonNull(email, "Email da biblioteca é obrigatório");

        if (name.isBlank()) {
            throw new IllegalArgumentException("Nome da biblioteca não pode ser vazio");
        }

        if (email.isBlank()) {
            throw new IllegalArgumentException("Nome da biblioteca não pode ser vazio");
        }
    }

    public void deactivate() {
        this.status = LibraryStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status == LibraryStatus.ACTIVE;
    }

    public LibraryId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LibraryStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
