package io.github.leocklaus.sistema_biblioteca.user.domain.model;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final UserId id;
    private final LibraryId libraryId;
    private final String email;
    private final String passwordHash;
    private final Set<Role> roles;
    private final UserStatus status;
    private final Instant createdAt;

    private User(
            UserId id,
            LibraryId libraryId,
            String email,
            String passwordHash,
            Set<Role> roles,
            UserStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static User createAdmin(
            LibraryId libraryId,
            String email,
            String passwordHash
    ) {
        validate(email, passwordHash);

        return new User(
                UserId.newId(),
                libraryId,
                email,
                passwordHash,
                new HashSet<>(Set.of(Role.adminOf(libraryId))),
                UserStatus.ACTIVE,
                Instant.now()
        );
    }

    public static User createLibrarian(
            LibraryId libraryId,
            String email,
            String passwordHash
    ) {
        validate(email, passwordHash);

        return new User(
                UserId.newId(),
                libraryId,
                email,
                passwordHash,
                new HashSet<>(Set.of(Role.librarianOf(libraryId))),
                UserStatus.ACTIVE,
                Instant.now()
        );
    }

    public static User rehydrate(
            UserId id,
            LibraryId libraryId,
            String email,
            String passwordHash,
            Set<Role> roles,
            UserStatus status,
            Instant createdAt
    ) {
        return new User(
                id,
                libraryId,
                email,
                passwordHash,
                new HashSet<>(roles),
                status,
                createdAt
        );
    }

    private static void validate(String email, String passwordHash) {
        if (email == null || email.isBlank()) {
            throw new DomainException("Email do usuário é obrigatório");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new DomainException("Senha inválida");
        }
    }

    public void assignAdminRole(LibraryId libraryId) {
        roles.add(Role.adminOf(libraryId));
    }

    public void assignLibrarianRole(LibraryId libraryId) {
        roles.add(Role.librarianOf(libraryId));
    }

    public boolean isAdminOf(LibraryId libraryId) {
        return roles.contains(Role.adminOf(libraryId));
    }

    public boolean isLibrarianOf(LibraryId libraryId) {
        return roles.contains(Role.librarianOf(libraryId));
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roles);
    }

    public boolean belongsToLibrary(LibraryId libraryId) {
        return this.libraryId.equals(libraryId);
    }

    public boolean canAccessLibrary(LibraryId libraryId) {
        return isAdminOf(libraryId) || isLibrarianOf(libraryId);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public UserId getId() {
        return id;
    }

    public LibraryId getLibraryId() {
        return libraryId;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<Role> rolesSnapshot() {
        return Set.copyOf(roles);
    }
}
