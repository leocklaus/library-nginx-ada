package io.github.leocklaus.sistema_biblioteca.book.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;
import io.github.leocklaus.sistema_biblioteca.book.infrastructure.persistence.BookEntity;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public static BookEntity toEntity(Book book) {
        return new BookEntity(
                book.getId().value(),
                book.getLibraryId().value(),
                book.getIsbn().value(),
                book.getTitle(),
                book.getAuthor()
        );
    }

    public Book toDomain(BookEntity entity) {
        return Book.rehydrate(
                new BookId(entity.getId()),
                new LibraryId(entity.getLibraryId()),
                new ISBN(entity.getIsbn()),
                entity.getTitle(),
                entity.getAuthor()
        );
    }
}
