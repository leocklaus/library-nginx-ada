package io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.mapper;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.Inventory;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.InventoryId;
import io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.persistence.InventoryEntity;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;

public class InventoryMapper {
    private InventoryMapper() {}

    public static InventoryEntity toEntity(Inventory inventory) {
        return new InventoryEntity(
                inventory.getId().value(),
                inventory.getLibraryId().value(),
                inventory.getBookId().value(),
                inventory.getTotalQuantity(),
                inventory.getAvailableQuantity()
        );
    }

    public static Inventory toDomain(InventoryEntity entity) {
        return Inventory.rehydrate(
                new InventoryId(entity.getId()),
                new LibraryId(entity.getLibraryId()),
                new BookId(entity.getBookId()),
                entity.getTotalQuantity(),
                entity.getAvailableQuantity()
        );
    }
}
