package io.github.leocklaus.sistema_biblioteca.book.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<BookEntity, UUID> {
    boolean existsByLibraryIdAndIsbn(UUID libraryId, String isbn);
    List<BookEntity> findByLibraryId(UUID libraryId);
    Optional<BookEntity> findByLibraryIdAndId(UUID libraryId, UUID bookId);
}
