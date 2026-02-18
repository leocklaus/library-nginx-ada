package io.github.leocklaus.sistema_biblioteca.user.domain.repository;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.UserId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UserId id);

    boolean existsByEmail(String email);

    List<User> findAllByLibraryId(LibraryId libraryId);

    Optional<User> findByEmail(String email);

    List<User> findLibrariansByLibraryId(UUID libraryId);
}
