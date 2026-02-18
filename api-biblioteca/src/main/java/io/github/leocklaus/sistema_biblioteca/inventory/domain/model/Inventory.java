package io.github.leocklaus.sistema_biblioteca.inventory.domain.model;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.Book;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.book.domain.model.ISBN;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.DomainException;

public class Inventory {
    private final InventoryId id;
    private final LibraryId libraryId;
    private final BookId bookId;
    private int totalQuantity;
    private int availableQuantity;

    private Inventory(
            InventoryId id,
            LibraryId libraryId,
            BookId bookId,
            int totalQuantity,
            int availableQuantity
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.bookId = bookId;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }

    public static Inventory create(
            LibraryId libraryId,
            BookId bookId,
            int initialQuantity
    ) {
        if (initialQuantity < 0) {
            throw new DomainException("Quantidade inicial não pode ser negativa");
        }

        return new Inventory(
                InventoryId.newId(),
                libraryId,
                bookId,
                initialQuantity,
                initialQuantity
        );
    }

    public static Inventory rehydrate(
            InventoryId id,
            LibraryId libraryId,
            BookId bookId,
            int totalQuantity,
            int availableQuantity
    ) {
        return new Inventory(
                InventoryId.newId(),
                libraryId,
                bookId,
                totalQuantity,
                availableQuantity
        );
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new DomainException("Quantidade a adicionar deve ser positiva");
        }

        this.totalQuantity += amount;
        this.availableQuantity += amount;
    }

    public void removeQuantity(int amount) {
        if (amount <= 0) {
            throw new DomainException("Quantidade a remover deve ser positiva");
        }

        if (amount > availableQuantity) {
            throw new DomainException("Quantidade indisponível em estoque");
        }

        this.totalQuantity -= amount;
        this.availableQuantity -= amount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public BookId getBookId() {
        return bookId;
    }

    public LibraryId getLibraryId() {
        return libraryId;
    }

    public InventoryId getId(){
        return id;
    }
}
