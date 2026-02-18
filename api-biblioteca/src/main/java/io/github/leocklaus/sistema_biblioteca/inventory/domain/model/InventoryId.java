package io.github.leocklaus.sistema_biblioteca.inventory.domain.model;

import java.util.Objects;
import java.util.UUID;

public record InventoryId(UUID value) {

    public InventoryId {
        Objects.requireNonNull(value, "InventoryId não pode ser nulo");
    }

    public static InventoryId newId() {
        return new InventoryId(UUID.randomUUID());
    }
}
