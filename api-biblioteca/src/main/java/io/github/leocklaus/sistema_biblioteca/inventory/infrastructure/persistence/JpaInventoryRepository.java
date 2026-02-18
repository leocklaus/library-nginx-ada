package io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, UUID> {
    Optional<InventoryEntity> findByLibraryIdAndBookId(
            UUID libraryId,
            UUID bookId
    );
}
