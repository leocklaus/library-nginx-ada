package io.github.leocklaus.sistema_biblioteca.inventory.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(
        name = "inventories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_inventory_library_book",
                        columnNames = {"library_id", "book_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class InventoryEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "library_id", nullable = false)
    private UUID libraryId;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    public InventoryEntity(
            UUID id,
            UUID libraryId,
            UUID bookId,
            int totalQuantity,
            int availableQuantity
    ) {
        this.id = id;
        this.libraryId = libraryId;
        this.bookId = bookId;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
    }
}
