package io.github.leocklaus.sistema_biblioteca.inventory.api.dto;

public record InventoryResponse(
        int totalQuantity,
        int availableQuantity
) {
}
