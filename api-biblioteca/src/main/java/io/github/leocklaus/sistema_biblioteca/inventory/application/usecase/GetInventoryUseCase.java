package io.github.leocklaus.sistema_biblioteca.inventory.application.usecase;

import io.github.leocklaus.sistema_biblioteca.book.domain.model.BookId;
import io.github.leocklaus.sistema_biblioteca.inventory.api.dto.InventoryResponse;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.exception.InventoryNotFoundException;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.model.Inventory;
import io.github.leocklaus.sistema_biblioteca.inventory.domain.repository.InventoryRepository;
import io.github.leocklaus.sistema_biblioteca.library.domain.model.LibraryId;
import io.github.leocklaus.sistema_biblioteca.shared.exception.ForbiddenOperationException;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUser;
import io.github.leocklaus.sistema_biblioteca.user.domain.model.AuthenticatedUserProvider;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetInventoryUseCase {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final InventoryRepository inventoryRepository;

    public GetInventoryUseCase(AuthenticatedUserProvider authenticatedUserProvider, InventoryRepository inventoryRepository) {
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryResponse execute(UUID libraryId, UUID bookId) {

        AuthenticatedUser user = authenticatedUserProvider.get();

        LibraryId libId = new LibraryId(libraryId);
        BookId bId = new BookId(bookId);

        if (!user.isAdminOf(libId) && !user.isLibrarianOf(libId)) {
            throw new ForbiddenOperationException("Sem permissão para consultar inventário");
        }

        Inventory inventory = inventoryRepository
                .findByLibraryIdAndBookId(libId, bId)
                .orElseThrow(() -> new InventoryNotFoundException(libId, bId));

        return new InventoryResponse(
                inventory.getTotalQuantity(),
                inventory.getAvailableQuantity()
        );
    }
}
