package io.github.leocklaus.sistema_biblioteca.book.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.domain.exception.BookNotFoundException;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.book.domain.repository.BookRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetBookByLibraryIdAndBookIdUseCase {
    private final BookRepository bookRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetBookByLibraryIdAndBookIdUseCase(BookRepository bookRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.bookRepository = bookRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public Book execute(LibraryId libraryId, BookId bookId) {

        AuthenticatedUser user = authenticatedUserProvider.get();

        if (!user.canAccessLibrary(libraryId)) {
            throw new ForbiddenOperationException("Sem permissão para acessar os livros desta biblioteca");
        }

        return bookRepository.findByLibraryIdAndId(libraryId, bookId)
                .orElseThrow(BookNotFoundException::new);
    }
}
