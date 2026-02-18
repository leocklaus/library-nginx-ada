package io.github.leocklaus.sistema_biblioteca.library.domain.repository;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository {
    Library save(Library library);

    Optional<Library> findById(LibraryId id);

    boolean existsByEmail(String email);

    List<Library> findAll();
}
