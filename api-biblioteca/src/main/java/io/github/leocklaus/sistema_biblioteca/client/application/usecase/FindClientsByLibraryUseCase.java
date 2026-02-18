package io.github.leocklaus.sistema_biblioteca.client.application.usecase;

import io.github.leocklaus.sistema_biblioteca.client.api.dto.ClientResponse;
import io.github.leocklaus.sistema_biblioteca.client.domain.repository.ClientRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindClientsByLibraryUseCase {
    private final ClientRepository clientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public FindClientsByLibraryUseCase(
            ClientRepository clientRepository,
            AuthenticatedUserProvider authenticatedUserProvider
    ) {
        this.clientRepository = clientRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public List<ClientResponse> execute(LibraryId libraryId) {
        AuthenticatedUser user = authenticatedUserProvider.get();

        if (!user.canAccessLibrary(libraryId)) {
            throw new ForbiddenOperationException(
                    "Sem permissão para consultar clientes desta biblioteca"
            );
        }

        return clientRepository.findAllByLibraryId(libraryId)
                .stream()
                .map(ClientResponse::from)
                .toList();
    }
}
