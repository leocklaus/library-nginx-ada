package io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.persistence;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.Inventory;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.repository.InventoryRepository;
import io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.mapper.InventoryMapper;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JpaInventoryRepository repository;

    public InventoryRepositoryImpl(JpaInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Inventory inventory) {
        InventoryEntity entity = InventoryMapper.toEntity(inventory);
        repository.save(entity);
    }

    @Override
    public Optional<Inventory> findByLibraryIdAndBookId(
            LibraryId libraryId,
            BookId bookId
    ) {
        return repository
                .findByLibraryIdAndBookId(
                        libraryId.value(),
                        bookId.value()
                )
                .map(InventoryMapper::toDomain);
    }

    @Override
    public int getAvailableQuantity(LibraryId libraryId, BookId bookId) {
        return repository.findByLibraryIdAndBookId(libraryId.value(), bookId.value())
                .map(InventoryEntity::getAvailableQuantity)
                .orElse(0);
    }

    @Override
    @Transactional
    public void decrement(LibraryId libraryId, BookId bookId) {
        InventoryEntity entity = repository.findByLibraryIdAndBookId(libraryId.value(), bookId.value())
                .orElseThrow(() -> new IllegalArgumentException("Livro não cadastrado no estoque"));

        if (entity.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis");
        }

        entity.setAvailableQuantity(entity.getAvailableQuantity() - 1);
        repository.save(entity);
    }

    @Transactional
    @Override
    public void increment(LibraryId libraryId, BookId bookId) {
        InventoryEntity entity = repository.findByLibraryIdAndBookId(libraryId.value(), bookId.value())
                .orElseThrow(() -> new IllegalArgumentException("Livro não cadastrado no estoque"));

        entity.setAvailableQuantity(entity.getAvailableQuantity() + 1);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void addNewCopies(LibraryId libraryId, BookId bookId, int quantity) {
        InventoryEntity entity = repository.findByLibraryIdAndBookId(libraryId.value(), bookId.value())
                .orElseThrow(() -> new IllegalArgumentException("Livro não cadastrado no estoque"));

        entity.setTotalQuantity(entity.getTotalQuantity() + quantity);
        entity.setAvailableQuantity(entity.getAvailableQuantity() + quantity);
        repository.save(entity);
    }

    @Override
    public int getTotalQuantity(LibraryId libraryId, BookId bookId) {
        return repository.findByLibraryIdAndBookId(libraryId.value(), bookId.value())
                .map(InventoryEntity::getTotalQuantity)
                .orElse(0);
    }

}
