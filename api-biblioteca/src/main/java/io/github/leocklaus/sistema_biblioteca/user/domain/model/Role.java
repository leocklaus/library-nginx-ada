package io.github.leocklaus.sistema_biblioteca.user.domain.model;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.Objects;

public class Role {

    private final RoleType type;
    private final LibraryId libraryId;

    private Role(RoleType type, LibraryId libraryId) {
        this.type = type;
        this.libraryId = libraryId;
    }

    public static Role adminOf(LibraryId libraryId) {
        return new Role(RoleType.ADMIN, libraryId);
    }

    public static Role librarianOf(LibraryId libraryId) {
        return new Role(RoleType.LIBRARIAN, libraryId);
    }

    public RoleType getType() {
        return type;
    }

    public LibraryId getLibraryId() {
        return libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return type == role.type && Objects.equals(libraryId, role.libraryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, libraryId);
    }

    public String asString() {
        return type.name() + ":" + libraryId.value();
    }

    public static Role fromString(String value) {
        String[] parts = value.split(":");
        RoleType type = RoleType.valueOf(parts[0]);
        LibraryId libraryId = new LibraryId(java.util.UUID.fromString(parts[1]));
        return new Role(type, libraryId);
    }
}
