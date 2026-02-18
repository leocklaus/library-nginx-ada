package io.github.leocklaus.sistema_biblioteca.book.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.repository.BookRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional(readOnly = true)
public class GetBooksByLibraryUseCase {
    private final BookRepository bookRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetBooksByLibraryUseCase(BookRepository bookRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.bookRepository = bookRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<Book> getByLibrary(UUID libraryId) {

        LibraryId libId = new LibraryId(libraryId);
        AuthenticatedUser user = authenticatedUserProvider.get();

        if (!user.canAccessLibrary(libId)) {
            throw new ForbiddenOperationException("Sem permissão para acessar os livros desta biblioteca");
        }

        return bookRepository.findByLibraryId(libId);
    }
}
