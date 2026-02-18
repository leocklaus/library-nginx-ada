package io.github.leocklaus.sistema_biblioteca.user.application.usecase;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.api.dto.RegisterLibrarianRequest;
import io.github.leocklaus.sistema_biblioteca.user.api.dto.RegisterLibrarianResponse;
import io.github.leocklaus.sistema_biblioteca.user.application.service.LibrarianFactory;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.User;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateLibrarianUseCase {
    private final UserRepository userRepository;
    private final LibrarianFactory librarianFactory;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public CreateLibrarianUseCase(UserRepository userRepository, LibrarianFactory librarianFactory, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.librarianFactory = librarianFactory;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public RegisterLibrarianResponse register(UUID libraryId, RegisterLibrarianRequest request) {

        AuthenticatedUser authenticated = authenticatedUserProvider.get();

        if (!authenticated.isAdminOf(new LibraryId(libraryId))) {
            throw new ForbiddenOperationException("Somente admin pode cadastrar bibliotecários");
        }

        User librarian = librarianFactory.create(
                request.email(),
                request.password(),
                libraryId
        );

        userRepository.save(librarian);

        return new RegisterLibrarianResponse(librarian.getId().value());
    }
}
