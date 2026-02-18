package io.github.leocklaus.sistema_biblioteca.book.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.api.dto.RegisterBookRequest;
import io.github.leocklaus.sistema_biblioteca.book.api.dto.RegisterBookResponse;
import io.github.leocklaus.sistema_biblioteca.book.domain.exception.DuplicateISBNException;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;
import io.github.leocklaus.sistema_biblioteca.book.domain.repository.BookRepository;
import io.github.leocklaus.sistema_biblioteca.library.application.usecase.GetLibraryUseCase;
import io.github.leocklaus.sistema_biblioteca.library.domain.exception.LibraryNotFoundException;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.Library;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.library.domain.repository.LibraryRepository;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterBookUseCase {
    private final BookRepository bookRepository;
    private final GetLibraryUseCase getLibraryUseCase;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public RegisterBookUseCase(
            BookRepository bookRepository,
            GetLibraryUseCase getLibraryUseCase, AuthenticatedUserProvider authenticatedUserProvider
    ) {
        this.bookRepository = bookRepository;
        this.getLibraryUseCase = getLibraryUseCase;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public RegisterBookResponse register(
            UUID libraryId,
            RegisterBookRequest request
    ) {

        LibraryId libraryIdEntity = new LibraryId(libraryId);

        Library library = getLibraryUseCase.getById(libraryId);

        AuthenticatedUser user = authenticatedUserProvider.get();

        if (!user.isAdminOf(libraryIdEntity)) {
            throw new ForbiddenOperationException(
                    "Usuário não é admin desta biblioteca"
            );
        }

        ISBN isbn = new ISBN(request.isbn());

        boolean exists = bookRepository.existsByLibraryIdAndIsbn(
                libraryIdEntity,
                isbn
        );

        if (exists) {
            throw new DuplicateISBNException(libraryId, isbn);
        }

        Book book = Book.create(
                libraryIdEntity,
                isbn,
                request.title(),
                request.author()
        );

        bookRepository.save(book);

        return new RegisterBookResponse(book.getId().value());
    }
}
