package io.github.leocklaus.sistema_biblioteca.client.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.client.domain.model.Client;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaClientRepository extends JpaRepository<ClientEntity, UUID> {
    boolean existsByLibraryIdAndEmail(UUID libraryId, String email);
    List<ClientEntity> findAllByLibraryId(UUID libraryId);

}
