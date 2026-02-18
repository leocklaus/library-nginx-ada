package io.github.leocklaus.sistema_biblioteca.book.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;
import io.github.leocklaus.sistema_biblioteca.book.domain.repository.BookRepository;
import io.github.leocklaus.sistema_biblioteca.book.infrastructure.mapper.BookMapper;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final JpaBookRepository repository;
    private final BookMapper mapper;

    public BookRepositoryImpl(JpaBookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByLibraryIdAndIsbn(
            LibraryId libraryId,
            ISBN isbn
    ) {
        return repository.existsByLibraryIdAndIsbn(
                libraryId.value(),
                isbn.value()
        );
    }

    @Override
    public void save(Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        repository.save(entity);
    }

    @Override
    public List<Book> findByLibraryId(LibraryId libraryId) {
        return repository.findByLibraryId(libraryId.value())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Book> findByLibraryIdAndId(LibraryId libraryId, BookId id) {
        return repository.findByLibraryIdAndId(libraryId.value(), id.value())
                .map(mapper::toDomain);
    }
}
