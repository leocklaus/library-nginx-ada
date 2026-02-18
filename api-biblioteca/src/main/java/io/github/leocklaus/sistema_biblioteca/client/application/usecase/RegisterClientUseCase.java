package io.github.leocklaus.sistema_biblioteca.client.application.usecase;

import io.github.leocklaus.sistema_biblioteca.client.api.dto.RegisterClientRequest;
import io.github.leocklaus.sistema_biblioteca.client.api.dto.RegisterClientResponse;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.repository.ClientRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterClientUseCase {
    private final ClientRepository repository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public RegisterClientUseCase(ClientRepository repository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.repository = repository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    public RegisterClientResponse execute(
            RegisterClientRequest request,
            UUID libraryId
    ){

        AuthenticatedUser user = authenticatedUserProvider.get();

        LibraryId libraryIdEntity = new LibraryId(libraryId);

        if (!user.canAccessLibrary(libraryIdEntity)) {
            throw new ForbiddenOperationException("Sem permissão para cadastrar clientes");
        }

        if (repository.existsByLibraryIdAndEmail(
                libraryIdEntity,
                request.email()
        )) {
            throw new DomainException("Já existe cliente com esse email nessa biblioteca");
        }

        Client client = Client.create(
                libraryIdEntity,
                request.name(),
                request.email()
        );

        repository.save(client);

        return new RegisterClientResponse(
                client.getId().value()
        );

    }
}
