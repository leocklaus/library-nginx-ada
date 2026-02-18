package io.github.leocklaus.sistema_biblioteca.client.domain.repository;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void save(Client client);

    Optional<Client> findById(ClientId id);

    boolean existsByLibraryIdAndEmail(LibraryId libraryId, String email);

    List<Client> findAllByLibraryId(LibraryId libraryId);

}
