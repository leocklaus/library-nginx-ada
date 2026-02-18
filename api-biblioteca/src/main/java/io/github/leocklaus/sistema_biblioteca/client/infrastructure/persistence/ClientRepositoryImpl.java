package io.github.leocklaus.sistema_biblioteca.client.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.client.domain.model.ClientId;
import io.github.leocklaus.sistema_biblioteca.client.domain.repository.ClientRepository;
import io.github.leocklaus.sistema_biblioteca.client.infrastructure.mapper.ClientMapper;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final JpaClientRepository repository;

    public ClientRepositoryImpl(JpaClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Client client) {
        ClientEntity entity = ClientMapper.toEntity(client);
        repository.save(entity);
    }

    @Override
    public Optional<Client> findById(ClientId id) {
        return repository.findById(id.value())
                .map(ClientMapper::toDomain);
    }

    @Override
    public boolean existsByLibraryIdAndEmail(LibraryId libraryId, String email) {
        return repository.existsByLibraryIdAndEmail(
                libraryId.value(),
                email.toLowerCase()
        );
    }

    @Override
    public List<Client> findAllByLibraryId(LibraryId libraryId) {
        return repository.findAllByLibraryId(libraryId.value())
                .stream()
                .map(ClientMapper::toDomain)
                .toList();
    }

}
