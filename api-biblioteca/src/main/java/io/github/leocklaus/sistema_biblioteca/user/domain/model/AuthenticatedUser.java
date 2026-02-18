package io.github.leocklaus.sistema_biblioteca.user.domain.model;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.Set;

public class AuthenticatedUser {

    private final UserId id;
    private final Set<Role> roles;

    public AuthenticatedUser(UserId id, Set<Role> roles) {
        this.id = id;
        this.roles = roles;
    }

    public boolean isAdminOf(LibraryId libraryId) {
        return roles.contains(Role.adminOf(libraryId));
    }

    public boolean isLibrarianOf(LibraryId libraryId) {
        return roles.contains(Role.librarianOf(libraryId));
    }

    public boolean canAccessLibrary(LibraryId libraryId) {
        return isAdminOf(libraryId) || isLibrarianOf(libraryId);
    }

    public UserId getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return Set.copyOf(roles);
    }
}
