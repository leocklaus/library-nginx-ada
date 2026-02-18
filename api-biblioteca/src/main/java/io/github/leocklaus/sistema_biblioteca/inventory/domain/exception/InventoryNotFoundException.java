package io.github.leocklaus.sistema_biblioteca.inventory.domain.exception;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(LibraryId libId, BookId bId) {
        super("Inventory não encontrado");
    }
}
