package io.github.leocklaus.sistema_biblioteca.inventory.domain.repository;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.Inventory;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

import java.util.Optional;

public interface InventoryRepository {
    void save(Inventory inventory);

    Optional<Inventory> findByLibraryIdAndBookId(
            LibraryId libraryId,
            BookId bookId
    );

    int getAvailableQuantity(LibraryId libraryId, BookId bookId);
    void decrement(LibraryId libraryId, BookId bookId);
    void increment(LibraryId libraryId, BookId bookId);
    void addNewCopies(LibraryId libraryId, BookId bookId, int quantity);
    int getTotalQuantity(LibraryId libraryId, BookId bookId);
}
