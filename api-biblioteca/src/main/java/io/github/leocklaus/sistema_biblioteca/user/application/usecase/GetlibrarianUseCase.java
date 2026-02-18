package io.github.leocklaus.sistema_biblioteca.user.application.usecase;

import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.api.dto.LibrarianResult;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import io.github.leocklaus.sistema_biblioteca.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetlibrarianUseCase {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public GetlibrarianUseCase(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<LibrarianResult> execute(UUID libraryId) {

        AuthenticatedUser authenticated = authenticatedUserProvider.get();
        LibraryId libraryIdEntity = new LibraryId(libraryId);

        if (!authenticated.isAdminOf(libraryIdEntity)
                && !authenticated.isLibrarianOf(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para ver bibliotecários");
        }

        return userRepository.findLibrariansByLibraryId(libraryId)
                .stream()
                .map(user -> new LibrarianResult(
                        user.getId().value(),
                        user.getEmail()
                ))
                .toList();
    }
}
