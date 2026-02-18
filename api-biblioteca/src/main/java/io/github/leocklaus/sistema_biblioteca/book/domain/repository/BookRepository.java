package io.github.leocklaus.sistema_biblioteca.book.domain.repository;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    boolean existsByLibraryIdAndIsbn(LibraryId libraryId, ISBN isbn);

    void save(Book book);
    List<Book> findByLibraryId(LibraryId libraryId);
    Optional<Book> findByLibraryIdAndId(LibraryId libraryId, BookId bookId);
}
